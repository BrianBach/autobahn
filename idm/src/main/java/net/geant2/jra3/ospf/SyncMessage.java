/**
 * SyncMessage.java
 * 
 * 2006-09-05
 */
package net.geant2.jra3.ospf;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.DataOutputStream;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import net.geant2.jra3.ospf.lsa.OspfLsa;
import net.geant2.jra3.ospf.lsa.OspfLsaOpaque;
import net.geant2.jra3.network.Link;

/**
 * This class is responsible for actual sending messages to OSPF daemon
 * 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */

class SyncMessage extends BaseMessage {
    
    public static final int MSG_REGISTER_OPAQUE = 1;
    public static final int MSG_UNREGISTER_OPAQUE = 2;
    public static final int MSG_REGISTER_EVENT = 3;
    public static final int MSG_SYNC_LSDB = 4;
    public static final int MSG_ORIGINATE_REQUEST = 5;
    public static final int MSG_DELETE_REQUEST = 6;
    
    /**
     * @param input input stream for reading
     * @param output output stream for writing
     */
    public SyncMessage(InputStream input, OutputStream output) {
        super(input, output);
    }
    
    private byte[] toAddress(String addr) throws OspfException {
        try {
            byte[] a = InetAddress.getByName(addr).getAddress();
            if (a.length != 4)
                throw new OspfException("toAddress: incorrect length" + a.length);
            return a;
        } catch (UnknownHostException ex) {
            throw new OspfException("toAddress: " + addr + ", " + ex.getMessage());
        }
    }
    
    /**
     * Reads reply from the OSPF daemon
     * @return reply code
     * @throws IOException
     * @throws OspfException
     */
    protected int readReply() throws IOException, OspfException {
        
        readHeader();
        if (lastMsgType != MSG_REPLY)
            throw new OspfException("readReply: wrong type: " + lastMsgType);
        
        int res = input.readByte();
        readPad(3);
        return res;
    }
    
    /**
     * Registers opaque in the OSPF daemon
     * @param lsaType LSA type
     * @param opaqueType opaque type
     * @throws IOException
     * @throws OspfException
     */
    public void writeRegisterOpaque(int lsaType, int opaqueType) throws 
                                IOException, OspfException {
        
        writeHeader(MSG_REGISTER_OPAQUE, 4);
        output.writeByte(lsaType);
        output.writeByte(opaqueType);
        writePad(2);
        
        int res = readReply();
        if (res != MSG_REPLY_OK)
            throw new OspfException("registerOpaque error: " + res);
    }
    
    /**
     * Unregisters opaque in the OSPF daemon
     * @param lsaType LSA type
     * @param opaqueType opaque type
     * @throws IOException
     * @throws OspfException
     */
    public void writeUnregisterOpaque(int lsaType, int opaqueType) throws 
                            IOException, OspfException {
                
        writeHeader(MSG_UNREGISTER_OPAQUE, 4);
        output.writeByte(lsaType);
        output.writeByte(opaqueType);
        writePad(2);
        
        int res = readReply();
        if (res != MSG_REPLY_OK)
            throw new OspfException("writeUnregisterOpaque error: " + res);
    }
    
    /**
     * Tells the OSPF daemon what kind of events user wants to receive
     * @param mask bit mask with events
     * @throws IOException
     * @throws OspfException
     */
    public void writeEvents(int mask) throws 
                        IOException, OspfException {
        
        writeHeader(MSG_REGISTER_EVENT, 4);
        output.writeShort(mask);
        output.writeByte(OspfLsa.FILTER_ANY_ORIGIN);
        output.writeByte(0); // all areas
        
        int res = readReply();
        if (res != MSG_REPLY_OK)
            throw new OspfException("writeEvents error: " + res);
    }
    
    /**
     * Causes the OSPF daemon to send its opaque types to user
     * @throws OspfException
     */
    public void writeSyncLsdb() throws IOException, OspfException {
                
        //writeEvents(0xFFFF);
        writeHeader(MSG_SYNC_LSDB, 4);
        output.writeShort(0xFFFF); // all lsa
        output.writeByte(OspfLsa.FILTER_ANY_ORIGIN);
        output.writeByte(0); // all areas
        
        int res = readReply();
        if (res != MSG_REPLY_OK)
            throw new OspfException("writeSyncLsdb error: " + res);
    }
    
    /**
     * Sends user's opaque to the OSPF daemon
     * @param ifAddr interface address
     * @param areaId area id
     * @param lsaType LSA type
     * @param opaqueType opaque type
     * @param opaqueId opaque id
     * @param opaqueData custom data
     * @throws IOException
     * @throws OspfException
     */
    public void writeLsaOrginate(String ifAddr, String areaId,
            int lsaType, int opaqueType, int opaqueId,
            Object opaqueData) throws IOException, OspfException {
        
        if (!OspfLsaOpaque.isOpaqueLsa(lsaType))
            throw new OspfException("Cannot originate non-opaque lsa type: " + lsaType);
        if (opaqueData == null)
        	throw new IllegalArgumentException("opaqueData cannot be null");

        int opaqueLength = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(opaqueData);
        oos.close();
        opaqueLength = baos.size();
                
        int length = OspfLsa.OSPF_LSA_HEADER_SIZE + opaqueLength;
        if (length > OspfLsa.OSPF_LSA_MAX_SIZE)
            throw new OspfException("Too much data in opaque: " + length);
        
        writeHeader(MSG_ORIGINATE_REQUEST, length + 8);
        output.write(toAddress(ifAddr));
        output.write(toAddress(areaId));
        output.writeShort(0); //age
        output.writeByte(0); // options
        output.writeByte(lsaType);
        output.writeByte(opaqueType);
        output.writeByte(0);
        output.writeShort(opaqueId);
        output.writeInt(0); // advRouter
        output.writeInt(0); // seq num
        output.writeShort(0); // checksum
        output.writeShort(length);
        
        output.write(baos.toByteArray());
        baos.close();
        
        int res = readReply();
        if (res != MSG_REPLY_OK)
            throw new OspfException("writeLsaOriginate error: " + res);
    }
    

    /**
     * Inserts Link objects in tlv format
     * @param ifAddr
     * @param areaId
     * @param lsaType
     * @param opaqueType
     * @param opaqueId
     * @param link
     * @throws IOException
     * @throws OspfException
     */
    public void writeLsaOrginate(String ifAddr, String areaId,
            int lsaType, int opaqueType, int opaqueId,
            Link link) throws IOException, OspfException {
        
        if (!OspfLsaOpaque.isOpaqueLsa(lsaType))
            throw new OspfException("Cannot originate non-opaque lsa type: " + lsaType);

        if (link == null)
        	throw new IllegalArgumentException("link cannot be null");
        
        int opaqueLength = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream oos = new DataOutputStream(baos);
        LinkSerializer.serializeLink(oos, link);
        oos.close();
        opaqueLength = baos.size();
        baos.close();
        
        int length = OspfLsa.OSPF_LSA_HEADER_SIZE + opaqueLength;
        if (length > OspfLsa.OSPF_LSA_MAX_SIZE)
            throw new OspfException("Too much data in opaque: " + length);
        
        writeHeader(MSG_ORIGINATE_REQUEST, length + 8);
        output.write(toAddress(ifAddr));
        output.write(toAddress(areaId));
        output.writeShort(0); //age
        output.writeByte(0); // options
        output.writeByte(lsaType);
        output.writeByte(opaqueType);
        output.writeByte(0);
        output.writeShort(opaqueId);
        output.writeInt(0); // advRouter
        output.writeInt(0); // seq num
        output.writeShort(0); // checksum
        output.writeShort(length);
        
        try {
        	LinkSerializer.serializeLink(output, link);
        } catch (IOException e) {
            e.printStackTrace();
            throw new OspfException(e.getMessage());
        }
        
        int res = readReply();
        if (res != MSG_REPLY_OK)
            throw new OspfException("writeLsaOriginate error: " + res);
    }
   
    /**
     * Removes user's opaque
     * @param areaId area id
     * @param lsaType LSA type
     * @param opaqueType opaque type
     * @param opaqueId opaque id
     * @throws IOException
     * @throws OspfException
     */
    public void writeLsaDelete(String areaId, int lsaType, 
             int opaqueType, int opaqueId) throws 
                     IOException, OspfException {
        
        if (!OspfLsaOpaque.isOpaqueLsa(lsaType))
            throw new OspfException("Cannot delete non-opaque lsa type: " + lsaType);

        writeHeader(MSG_DELETE_REQUEST, 12);
        output.write(toAddress(areaId));
        output.writeByte(lsaType);
        output.writeByte(opaqueType);
        writePad(2);
        output.writeInt(opaqueId);
        
        int res = readReply();
        if (res != MSG_REPLY_OK)
            throw new OspfException("writeLsaDelete error: " + res);
    }
}
