package net.geant.autobahn.converter;

/**
 * Class represents IPv4 address. Provides numeric or bit representation, allows
 * comparing the addresses.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class IpAddress {

	private String bitRepr;

	/**
	 * Creates IPv4 address from the specified String.
	 * 
	 * @param address String address
	 */
	public IpAddress(String address) {
		bitRepr = convertIP2Bits(address);
	}
	
    private String convertIP2Bits(String ip) {
        long value = readIP(ip);
        
        String str = Long.toBinaryString(value);
        if(str.length() < 32) {
            String zeros = "";
            for(int i = 0; i < 32 - str.length(); i++) {
                zeros += "0";
            }
            
            str = zeros + str;
        }
        
        return str;
    }
	
    private long readIP(String ip) {
        String[] nums = ip.split("\\.");

        if(nums.length != 4)
            throw new IllegalArgumentException("Wrong address - should be i.e. 192.168.1.0");

        long value = 0;
        
        for(String n : nums) {
            int val = Integer.valueOf(n);

            value = value << 8;
            value += val;
        }
        
        return value;
    }
    
    /**
     * Returns String with bit representation of the address. 
     * 
     * @return String bit representation
     */
    public String getBits() {
    	return bitRepr;
    }
    
    /**
     * Returns numeric value of the address.
     * 
     * @return Long numeric value.
     */
    public long getValue() {
    	return Long.parseLong(bitRepr, 2);
    }
}
