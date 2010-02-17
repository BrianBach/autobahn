/**
 * BaseMessage.java
 * 
 * 2006-09-05
 */
package net.geant.autobahn.ospf;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Serves as a base class for SyncMessage and AsyncMessage.
 * Reads/writes message header
 * 
 * @author <a href="mailto:michalb@man.poznan.pl">Michal Balcerkiewicz</a>
 */

abstract class BaseMessage {
    
    public static final int OSPF_API_VERSION = 1;
    
    // message error codes
    public static final int MSG_REPLY = 10;
    public static final int MSG_REPLY_OK = 0;
    public static final int MSG_REPLY_NOINTERFACE = -1;
    public static final int MSG_REPLY_NOSUCHAREA = -2;
    public static final int MSG_REPLY_NOSUCHLSA = -3;
    public static final int MSG_REPLY_ILLEGALLSATYPE = -4;
    public static final int MSG_REPLY_OPAQUETYPEINUSE = -5;
    public static final int MSG_REPLY_OPAQUETYPENOTREGISTERED = -6;
    public static final int MSG_REPLY_NOTREADY = -7;
    public static final int MSG_REPLY_NOMEMORY = -8;
    public static final int MSG_REPLY_ERROR = -9;
    public static final int MSG_REPLY_UNDEF = -10;
    
    private static final int MIN_SEQUENCE = 1;
    private static final int MAX_SEQUENCE = 2147483647;
    private static int sequence;
    
    protected int lastMsgType, lastMsgLength; // set by readHeader
    protected DataInputStream input;
    protected DataOutputStream output;
        
    /**
     * Constructor
     * @param input input stream for reading
     * @param output output stream for writing
     */
    protected BaseMessage(InputStream input, OutputStream output) {
        if (input != null) 
            this.input = new DataInputStream(input);
        
        if (output != null) 
            this.output = new DataOutputStream(output);
    }
    
    private int getSequence() {
        return (sequence = (sequence < MAX_SEQUENCE) ? 
                sequence + 1 : MIN_SEQUENCE);
    }
    
    /**
     * Reads numBytes from stream, bytes read are ignored 
     * @param numBytes number of bytes to read
     * @throws OspfException
     */
    protected void readPad(int numBytes) throws IOException {
        
        if (input == null)
            throw new IOException("readPad: no input stream");
        
        for (int i=0; i<numBytes; i++)
            input.readByte();
    }
    
    /**
     * Writes numBytes to stream, values are zero
     * @param numBytes number of bytes to write
     * @throws OspfException
     */
    protected void writePad(int numBytes) throws IOException {
        
        if (output == null)
            throw new IOException("readPad: no output stream");
        
        for (int i=0; i<numBytes; i++)
            output.writeByte(0);
    }
    
    /**
     * Clears input stream
     * @return number of bytes read
     * @throws OspfException
     */
    protected int readFlush() throws IOException {
        
        if (input == null)
            throw new IOException("readFlush: no input stream");
        
        int num = input.available();
        readPad(num);
        return num;
    }
    
    
    /**
     * Sends header info to the OSPF API
     * @param type message type
     * @param length length of message
     * @throws OspfException
     */
    protected void writeHeader(int type, int length) throws
                        IOException {

        if (output == null)
            throw new IOException("writeHeader: no output stream");
        
        output.writeByte(OSPF_API_VERSION);
        output.writeByte(type);
        output.writeShort(length);
        output.writeInt(getSequence());
    }
    
    /**
     * Reads header off the OSPF API
     * @throws OspfException
     */
    protected void readHeader() throws IOException, OspfException {
        
        if (input == null)
            throw new IOException("readHeader: no input stream");
        
        int version = input.readUnsignedByte();
        if (version != OSPF_API_VERSION)
            throw new OspfException("readHeader: incorrect version: " + version);
            
        lastMsgType = input.readUnsignedByte();
        lastMsgLength = input.readUnsignedShort();
        input.readInt();
    }
}
