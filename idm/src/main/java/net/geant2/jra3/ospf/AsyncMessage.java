/**
 * AsyncMessage.java
 * 
 * 2006-09-05
 */
package net.geant2.jra3.ospf;

import java.util.List;
import java.io.Closeable;
import java.io.InputStream;
import java.io.IOException;
import net.geant2.jra3.ospf.lsa.OspfLsa;

/**
 * Handles incoming messages from OSPF daemon.
 * Users that provided implementation of <code>OspfAsync</code> interface
 * are notified about these events
 * 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */

class AsyncMessage extends BaseMessage 
                        implements Runnable, Closeable {
    
    public static final int MSG_READY_NOTIFY = 11;
    public static final int MSG_LSA_UPDATE_NOTIFY = 12;
    public static final int MSG_LSA_DELETE_NOTIFY = 13;
    public static final int MSG_NEW_IF = 14;
    public static final int MSG_DEL_IF = 15;
    public static final int MSG_ISM_CHANGE = 16;
    public static final int MSG_NSM_CHANGE = 17;
    
    private Ospf ospf; // if async fails, we can disconnect 
    private List<OspfAsync> listeners;
    private Thread thread;
    private volatile boolean running = true;
        
    public AsyncMessage(Ospf ospf, InputStream input, List<OspfAsync> async) {
        super(input, null);
        this.ospf = ospf;
        listeners = async;
        thread = new Thread(this);
        thread.start();
    }
       
    /* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	public void close() throws IOException {

		running = false;
	}

	private String readAddress() throws IOException {
        
        int[] b = new int[4];
        for (int i=0; i<4; i++)
            b[i] = input.readUnsignedByte();
            
        return b[0] + "." + b[1] + "." + b[2] + "." + b[3];
    }
        
    /**
     * Reads MSG_READY_NOTIFY message
     * @throws IOException
     */
    protected void readReadyNotify() throws IOException {
        
        int lsaType = input.readUnsignedByte();
        int opaqueType = input.readUnsignedByte();
        readPad(2);
        String addr = readAddress();
        
        for (OspfAsync l : listeners)
            l.readyNotify(lsaType, opaqueType, addr);
    }

    /**
     * Reads MSG_LSA_UPDATE_NOTIFY message
     * @throws IOException
     * @throws OspfException
     */
    protected void readLsaChangeNotify() throws IOException, OspfException {
        
        String ifAddr = readAddress(); // used for lsa 9
        String areaId = readAddress(); // ignored for as external and lsa 11
        int selfOriginated = input.readUnsignedByte(); // 1 if self originated
        readPad(3);

        OspfLsa lsa = OspfLsa.createFromStream(input);
        
        for (OspfAsync l : listeners) 
            l.updateNotify(ifAddr, areaId, selfOriginated, lsa);
    }
    
    /**
     * Reads MSG_LSA_DELETE_NOTIFY message
     * @throws IOException
     * @throws OspfException
     */
    protected void readLsaDeleteNotify() throws IOException, OspfException {
        
        String ifAddr = readAddress(); // used for lsa 9
        String areaId = readAddress(); // ignored for as external and lsa 11
        int selfOriginated = input.readUnsignedByte(); // 1 if self originated
        readPad(3);
        
        OspfLsa lsa = OspfLsa.createFromStream(input);
        
        for (OspfAsync l : listeners) 
            l.deleteNotify(ifAddr, areaId, selfOriginated, lsa);
    }
    
    /**
     * Reads MSG_NEW_IF message 
     * @throws IOException
     */
    protected void readNewIf() throws IOException {
        
        String ifAddr = readAddress();
        String areaId = readAddress();
            
        for (OspfAsync l : listeners)
            l.newInterface(ifAddr, areaId);
    }
    
    /**
     * Reads MSG_DEL_IF message 
     * @throws IOException
     */
    protected void readDelIf() throws IOException {
        
        String ifAddr = readAddress();
        
        for (OspfAsync l : listeners)
            l.delInterface(ifAddr);
    }
    
    /**
     * Reads MSG_ISM_CHANGE message
     * @throws IOException
     */
    protected void readIsmChange() throws IOException {
        
        String ifAddr = readAddress();
        String areaId = readAddress();
        int status = input.readUnsignedByte();
        readPad(3);
        
        for (OspfAsync l : listeners)
            l.ismChange(ifAddr, areaId, status);
    }
    
    /**
     * Reads MSG_NSM_CHANGE message
     * @throws IOException
     */
    protected void readNsmChange() throws IOException {
        
        String ifAddr = readAddress();
        String nbrAddr = readAddress();
        String routerId = readAddress();
        int status = input.readUnsignedByte();
        readPad(3);
            
        for (OspfAsync l : listeners)
            l.nsmChange(ifAddr, nbrAddr, routerId, status);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
        
        while (running) {
            
            try {
                readHeader();
                
                switch (lastMsgType) {
                                
                case MSG_READY_NOTIFY:
                    readReadyNotify();
                    break;
                    
                case MSG_NEW_IF:
                    readNewIf();
                    break;
                    
                case MSG_DEL_IF:
                    readDelIf();
                    break;
                    
                case MSG_ISM_CHANGE:
                    readIsmChange();
                    break;
                    
                case MSG_NSM_CHANGE:
                    readNsmChange();
                    break;
                    
                case MSG_LSA_UPDATE_NOTIFY:
                    readLsaChangeNotify();
                    break;
                    
                case MSG_LSA_DELETE_NOTIFY:
                    readLsaDeleteNotify();
                    break;
                    
                default:
                    readFlush(); // unknown message received
                    break;       // but allow to continue
                }
            } catch(IOException e) {
            	// do nothing
            	break;
            } catch (Exception e) {
            	System.out.println(e.getMessage());
            	e.printStackTrace();
                try {
                    ospf.disconnect();
                } catch (Exception e1) { }
                break;
            }
        }
    }

    /**
     * @return the listeners
     */
    public List<OspfAsync> getListeners() {
        return listeners;
    }

    /**
     * @param listeners the listeners to set
     */
    public void setListeners(List<OspfAsync> listeners) {
        this.listeners = listeners;
    }
}
