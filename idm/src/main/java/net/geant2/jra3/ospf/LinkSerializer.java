package net.geant2.jra3.ospf;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataInput;
import java.io.IOException;
import net.geant2.jra3.network.*;
import net.geant2.jra3.ospf.lsa.OspfLsaOpaque;

/**
 * @author Michal
 */

public class LinkSerializer {
	
	static final int BYTE_BYTES = Byte.SIZE / Byte.SIZE;
	static final int SHORT_BYTES = Short.SIZE / Byte.SIZE;
	static final int INTEGER_BYTES = Integer.SIZE / Byte.SIZE;
	static final int LONG_BYTES = Long.SIZE / Byte.SIZE;
	static final int FLOAT_BYTES = Float.SIZE / Byte.SIZE;
	static final int DOUBLE_BYTES = Double.SIZE / Byte.SIZE;
	
	private enum OpaqueType {
		
		LINK_ID, LINK_KIND, LINK_BIDIRECTIONAL, LINK_DELAY,
		LINK_MANUAL_COST, LINK_MONETERY_COST, LINK_GRANULARITY,
		LINK_MIN_RES_CAPACITY, LINK_MAX_RES_CAPACITY,
		LINK_CAPACITY, LINK_RESILIENCY,
		LINK_TIMESTAMP, LINK_LOCALNAME, LINK_TYPE,
		LINK_OPER_STATE, LINK_ADMIN_STATE,
		PORT_ID, PORT_ADDRESS, PORT_TECHNOLOGY, PORT_BUNDLED, 
		NODE_ID, NODE_TYPE, NODE_ADDRESS, 
		NODE_NAME, NODE_COUNTRY, NODE_INSTITUTION,
		NODE_LONGITUDE, NODE_LATITUDE,
		PD_ID, PD_TYPE,
		AD_ID, AD_ASID, AD_NAME, AD_CLIENT_DOMAIN
	}
	
	protected DataOutput output;
	protected DataInput input;
	protected int bytesWrote;
	final int MAX_SIZE = OspfLsaOpaque.OSPF_LSA_MAX_SIZE - OspfLsaOpaque.OSPF_LSA_HEADER_SIZE;
	
	private LinkSerializer() { } 
	
	protected void writeTypeLength(int type, int length) throws IOException {	
				
		if (bytesWrote + SHORT_BYTES * 2 + length > MAX_SIZE)
			throw new IOException("MAX_SIZE exceeded - " + (bytesWrote + SHORT_BYTES * 2 + length));
		
		output.writeShort(type);
		output.writeShort(length);
		bytesWrote += SHORT_BYTES * 2;
	}
	
	protected void writeString(int type, String s) throws IOException {
	
		int length = s == null ? 0 : s.length();
		writeTypeLength(type, length);
		if (s != null)
			output.writeBytes(s);
		bytesWrote += length;
	}
	
	protected void writeValue(int type, Object value) throws IOException {

		if (value instanceof String) {
			throw new IOException("value as string");
			
		} else if (value instanceof Boolean) {
			writeTypeLength(type, 1);
			output.writeBoolean((Boolean)value);
			bytesWrote += 1;
		} else if (value instanceof Integer) {
			writeTypeLength(type, INTEGER_BYTES);
			output.writeInt((Integer)value);
			bytesWrote += INTEGER_BYTES;
		}else if (value instanceof Long) {
			writeTypeLength(type, LONG_BYTES);
			output.writeLong((Long)value);
			bytesWrote += LONG_BYTES;
		} else if (value instanceof Byte) {
			writeTypeLength(type, BYTE_BYTES);
			output.writeByte((Byte)value);
			bytesWrote += BYTE_BYTES;
		} else if (value instanceof Double) {
			writeTypeLength(type, DOUBLE_BYTES);
			output.writeDouble((Double)value);
			bytesWrote += DOUBLE_BYTES;
		}
	}
	
	protected void writeAdminDomain(AdminDomain ad) throws IOException {
		
		if (ad == null)
			throw new IllegalArgumentException("AdminDomain cannot be null");
			
		writeString(OpaqueType.AD_ID.ordinal(), ad.getBodID());
		writeString(OpaqueType.AD_ASID.ordinal(), ad.getASID());
		writeString(OpaqueType.AD_NAME.ordinal(), ad.getName());
		writeValue(OpaqueType.AD_CLIENT_DOMAIN.ordinal(), ad.isClientDomain());
	}
	
	protected void writeProvisioningDomain(ProvisioningDomain pd) throws IOException {
		
		if (pd == null)
			throw new IllegalArgumentException("ProvisioningDomain cannot be null");
		
		writeString(OpaqueType.PD_ID.ordinal(), pd.getBodID());
		writeString(OpaqueType.PD_TYPE.ordinal(), pd.getProvType());
	}
	
	protected void writeNode(Node node) throws IOException {
		
		if (node == null)
			throw new IllegalArgumentException("node cannot be null");
		
		writeString(OpaqueType.NODE_ID.ordinal(), node.getBodID());
		writeString(OpaqueType.NODE_TYPE.ordinal(), node.getType());
		writeString(OpaqueType.NODE_ADDRESS.ordinal(), node.getAddress());
		writeString(OpaqueType.NODE_NAME.ordinal(), node.getName());
		writeString(OpaqueType.NODE_COUNTRY.ordinal(), node.getCountry());
		writeString(OpaqueType.NODE_INSTITUTION.ordinal(), node.getInstitution());
/*		writeValue(OpaqueType.NODE_LONGITUDE.ordinal(), (Long)Long.valueOf(node.getLongitude()));
		writeValue(OpaqueType.NODE_LATITUDE.ordinal(), (Long)Long.valueOf(node.getLatitude()));
*/	}
	
	protected void writePort(Port port) throws IOException {
		
		if (port == null)
			throw new IllegalArgumentException("Port cannot null");
		
		writeString(OpaqueType.PORT_ID.ordinal(), port.getBodID());
		writeString(OpaqueType.PORT_ADDRESS.ordinal(), port.getAddress());
		writeString(OpaqueType.PORT_TECHNOLOGY.ordinal(), port.getTechnology());
		writeValue(OpaqueType.PORT_BUNDLED.ordinal(), port.isBundled());
	}
	
	protected void writeLink(Link link) throws IOException {
		
		if (link == null)
			throw new IllegalArgumentException("Link cannot be null");
		
		writeString(OpaqueType.LINK_ID.ordinal(), link.getBodID());
		writeValue(OpaqueType.LINK_KIND.ordinal(), link.getKind());
		writeValue(OpaqueType.LINK_BIDIRECTIONAL.ordinal(), link.isBidirectional());
		writeValue(OpaqueType.LINK_DELAY.ordinal(), link.getDelay());
		writeValue(OpaqueType.LINK_MANUAL_COST.ordinal(), link.getManualCost());
		writeValue(OpaqueType.LINK_MONETERY_COST.ordinal(), link.getMonetaryCost());
		writeValue(OpaqueType.LINK_MIN_RES_CAPACITY.ordinal(), link.getMinResCapacity());
		writeValue(OpaqueType.LINK_MAX_RES_CAPACITY.ordinal(), link.getMaxResCapacity());
		writeValue(OpaqueType.LINK_GRANULARITY.ordinal(), link.getGranularity());
		writeValue(OpaqueType.LINK_CAPACITY.ordinal(), link.getCapacity());
		writeString(OpaqueType.LINK_RESILIENCY.ordinal(), link.getResilience());
		//writeValue(OpaqueType.LINK_TIMESTAMP.ordinal(), link.getTimestamp().getTime());
/*		writeString(OpaqueType.LINK_LOCALNAME.ordinal(), link.getLocalName());
		writeValue(OpaqueType.LINK_TYPE.ordinal(), link.getType().getType());
		writeValue(OpaqueType.LINK_OPER_STATE.ordinal(), link.getOperationalState().getState());
		writeValue(OpaqueType.LINK_ADMIN_STATE.ordinal(), link.getAdministrativeState().getState());
*/	}
	
	public void serialize(DataOutput output, Link link) throws IOException {
		
		bytesWrote = 0;
		this.output = output;
		writeLink(link);
		writePort(link.getStartPort());
		writeNode(link.getStartPort().getNode());
		writeProvisioningDomain(link.getStartPort().getNode().getProvisioningDomain());
		writeAdminDomain(link.getStartPort().getNode().getProvisioningDomain().getAdminDomain());
		writePort(link.getEndPort());
		writeNode(link.getEndPort().getNode());
		writeProvisioningDomain(link.getEndPort().getNode().getProvisioningDomain());
		writeAdminDomain(link.getEndPort().getNode().getProvisioningDomain().getAdminDomain());
		
		// padding
		int pad = ((bytesWrote + SHORT_BYTES * 2) + 31) & ~31;
		pad -= bytesWrote + SHORT_BYTES * 2;
		writeTypeLength(Short.MAX_VALUE, pad);
		output.write(new byte[pad]);
		bytesWrote += pad;
	}
	
	// reads type and length and validates
	protected int readTypeLength(int type, Object value) throws IOException {
		
		int t = input.readUnsignedShort();
		if (t != type)
			throw new IOException("incorrect type - " + t);
		
		int length = input.readUnsignedShort();
		if (value instanceof Integer) {
			if (length != INTEGER_BYTES)
				throw new IOException("non int size - " + length);
		} else if (value instanceof Short) {
			if (length != SHORT_BYTES)
				throw new IOException("non short size - " + length);
		} else if (value instanceof Long) {
			if (length != LONG_BYTES)
				throw new IOException("non long size - " + length);
		} else if (value instanceof Double) {
			if (length != DOUBLE_BYTES)
				throw new IOException("non double size - " + length);
		} else if (value instanceof Byte) {
			if (length != BYTE_BYTES)
				throw new IOException("non byte size - " + length);
		} else if (value instanceof Boolean) {
			if (length != 1) 
				throw new IOException("non bool size - " + length);
		}
		// allow nulls and strings
		
		return length;
	}
	
	protected String readString(int type) throws IOException {
		
		byte[] buf = new byte[readTypeLength(type, null)];
		input.readFully(buf);
		
		StringBuilder sb = new StringBuilder();
		for (byte b : buf)
			sb.append((char)b);
		
		return sb.toString();
	}
	
	protected Object readValue(int type, Object value) throws IOException {
		
		readTypeLength(type, value);
		if (value instanceof Integer) 
			return input.readInt();
		else if (value instanceof Long)
			return input.readLong();
		else if (value instanceof Double)
			return input.readDouble();
		else if (value instanceof Byte)
			return input.readUnsignedByte();
		else if (value instanceof Boolean)
			return input.readBoolean();
			
		throw new IOException("unknown value - " + value.toString());
	}
	
	protected AdminDomain readAdminDomain() throws IOException {
		
		AdminDomain ad = new AdminDomain();
		ad.setBodID(readString(OpaqueType.AD_ID.ordinal()));
		ad.setASID(readString(OpaqueType.AD_ASID.ordinal()));
		ad.setName(readString(OpaqueType.AD_NAME.ordinal()));
		ad.setClientDomain((Boolean)readValue(OpaqueType.AD_CLIENT_DOMAIN.ordinal(), ad.isClientDomain()));
		return ad;
	}
	
	protected ProvisioningDomain readProvisioningDomain() throws IOException {
		
		ProvisioningDomain pd = new ProvisioningDomain();
		pd.setBodID(readString(OpaqueType.PD_ID.ordinal()));
		pd.setProvType(readString(OpaqueType.PD_TYPE.ordinal()));
		return pd;
	}
	
	protected Node readNode() throws IOException {
		
		Node node = new Node();
		node.setBodID(readString(OpaqueType.NODE_ID.ordinal()));
		node.setType(readString(OpaqueType.NODE_TYPE.ordinal()));
		node.setAddress(readString(OpaqueType.NODE_ADDRESS.ordinal()));
		node.setName(readString(OpaqueType.NODE_NAME.ordinal()));
		node.setCountry(readString(OpaqueType.NODE_COUNTRY.ordinal()));
		node.setInstitution(readString(OpaqueType.NODE_INSTITUTION.ordinal()));
/*		node.setLongitude(String.valueOf((Long)readValue(OpaqueType.NODE_LONGITUDE.ordinal(), Long.MIN_VALUE)));
		node.setLatitude(String.valueOf((Long)readValue(OpaqueType.NODE_LATITUDE.ordinal(), Long.MIN_VALUE)));
*/		return node;
	}
	
	protected Port readPort() throws IOException {
		
		Port port = new Port();
		port.setBodID(readString(OpaqueType.PORT_ID.ordinal()));
		port.setAddress(readString(OpaqueType.PORT_ADDRESS.ordinal()));
		port.setTechnology(readString(OpaqueType.PORT_TECHNOLOGY.ordinal()));
		port.setBundled((Boolean)readValue(OpaqueType.PORT_BUNDLED.ordinal(), port.isBundled()));
		return port;
	}
	
	protected Link readLink() throws IOException {
		
		Link link = new Link();
		link.setBodID(readString(OpaqueType.LINK_ID.ordinal()));
		link.setKind((Integer)readValue(OpaqueType.LINK_KIND.ordinal(), link.getKind()));
		link.setBidirectional((Boolean)readValue(OpaqueType.LINK_BIDIRECTIONAL.ordinal(), link.isBidirectional()));
		link.setDelay((Integer)readValue(OpaqueType.LINK_DELAY.ordinal(), link.getDelay()));
		link.setManualCost((Double)readValue(OpaqueType.LINK_MANUAL_COST.ordinal(), link.getManualCost()));
		link.setMonetaryCost((Double)readValue(OpaqueType.LINK_MONETERY_COST.ordinal(), link.getMonetaryCost()));
		link.setMinResCapacity((Long)readValue(OpaqueType.LINK_MIN_RES_CAPACITY.ordinal(), link.getMinResCapacity()));
		link.setMaxResCapacity((Long)readValue(OpaqueType.LINK_MAX_RES_CAPACITY.ordinal(), link.getMaxResCapacity()));
		link.setGranularity((Long)readValue(OpaqueType.LINK_GRANULARITY.ordinal(), link.getGranularity()));
		link.setCapacity((Long)readValue(OpaqueType.LINK_CAPACITY.ordinal(), link.getCapacity()));
		link.setResilience(readString(OpaqueType.LINK_RESILIENCY.ordinal()));
		//link.setTimestamp(new java.util.Date((Long)readValue(OpaqueType.LINK_TIMESTAMP.ordinal(), link.getTimestamp().getTime())));
/*		link.setLocalName(readString(OpaqueType.LINK_LOCALNAME.ordinal())); 
		link.setOperationalState(StateOper.getState((Integer) readValue(OpaqueType.LINK_OPER_STATE.ordinal(), link.getOperationalState().getState())));
		link.setAdministrativeState(StateAdmin.getState((Integer)readValue(OpaqueType.LINK_ADMIN_STATE.ordinal(), link.getAdministrativeState().getState())));
*/		
		return link;
	}
		
	public Link deserialize(DataInput input) throws IOException {

		this.input = input;
		Link link = readLink();
		link.setStartPort(readPort());
		link.getStartPort().setNode(readNode());
		
		ProvisioningDomain pd1 = readProvisioningDomain();
		link.getStartPort().getNode().setProvisioningDomain(pd1);
		
		AdminDomain ad1 = readAdminDomain();
		link.getStartPort().getNode().getProvisioningDomain().setAdminDomain(ad1);
		
		link.setEndPort(readPort());
		link.getEndPort().setNode(readNode());
		
		ProvisioningDomain pd2 = readProvisioningDomain();
		if(pd2.getBodID().equals(pd1.getBodID()))
			pd2 = pd1;
		link.getEndPort().getNode().setProvisioningDomain(pd2);

		AdminDomain ad2 = readAdminDomain();
		if(ad2.getBodID().equals(ad1.getBodID()))
			ad2 = ad1;
		link.getEndPort().getNode().getProvisioningDomain().setAdminDomain(ad2);
		
		// read padding
		int padnum = readTypeLength(Short.MAX_VALUE, null);
		byte[] pad = new byte[padnum];
		input.readFully(pad);
		return link;
	}
	
	public static void serializeLink(DataOutput output, Link link) throws IOException {
		
		LinkSerializer ls = new LinkSerializer();
		ls.serialize(output, link);
	}
	
	public void serialTest(DataOutput output) throws IOException { 
		
		bytesWrote = 0;
		this.output = output;
		//writeValue(0, 5);
		writeString(0, "test string");
		System.out.println("bytesWrote - " + bytesWrote);
	}
	
	public void deserialTest(DataInput input) throws IOException {
		
		this.input = input;
		String s = readString(0);
		//int n = (Integer)readValue(0, new Integer(0));
		System.out.println(s);
		
	}
	
	public static void serializeTest(DataOutput output) throws IOException {
		
		LinkSerializer ls = new LinkSerializer();
		ls.serialTest(output);
	}
	
	public static void deserializeTest(DataInput input) throws IOException {
		
		LinkSerializer ls = new LinkSerializer();
		ls.deserialTest(input);
	}
	
	public static Link deserializeLink(DataInput input) throws IOException {
		
		LinkSerializer ls = new LinkSerializer();
		return ls.deserialize(input);
	}
	
	public Link createSampleLink() {
		
		AdminDomain ad = new AdminDomain();
		ad.setASID("asid");
		ad.setBodID("admin id");
		ad.setName("admin domain");
		ProvisioningDomain pd = new ProvisioningDomain();
		pd.setAdminDomain(ad);
		pd.setBodID("prov id");
		pd.setProvType("type");
		Node node = new Node();
		node.setAddress("node address");
		node.setBodID("node id");
		node.setName("node");
		node.setProvisioningDomain(pd);
		Port port = new Port();
		port.setAddress("port address");
		port.setBodID("port id");
		port.setTechnology("tech");
		port.setNode(node);
		Link link = new Link();
		link.setBodID("link id");
		link.setEndPort(port);
		link.setStartPort(port);
		
		return link;
	}
	
	public static Link createLink() {
		
		LinkSerializer ls = new LinkSerializer();
		return ls.createSampleLink();
	}
	
	public static void main(String[] args) throws Exception {
		
		Link link = LinkSerializer.createLink();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream oos = new DataOutputStream(baos);
		
		LinkSerializer.serializeLink(oos, link);
		//LinkSerializer.serializeTest(oos);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		DataInputStream dis = new DataInputStream(bais);
		LinkSerializer.deserializeLink(dis);
		//LinkSerializer.deserializeTest(dis);
		
		dis.close();
		bais.close();
		oos.close();
		baos.close();
		
		System.out.println("buffer size - " + baos.size());
		
	}
}
