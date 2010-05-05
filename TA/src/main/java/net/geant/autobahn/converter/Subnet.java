package net.geant.autobahn.converter;

/**
 * Represents CIDR network address. Generates successive IP addresses belonging to
 * the network.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class Subnet {

    private String subnet;
    
    private long min;
    private long max;
    private long current;
    private IpAddress address;
    
    /**
     * Creates a subnet from the specified address.
     * 
     * @param subnet String address of the subnet.
     */
    public Subnet(String subnet) {
        this.subnet = subnet;
        parse();
    }
    
    private void parse() {
        // Split the Subnet, e.g. 192.168.1.1/16
        String[] parts = subnet.split("\\/");
        
        if (parts.length != 2) {
            throw new IllegalArgumentException("Subnet must have an IP and a mask");
        }
        
        String network = parts[0];  // e.g. 192.168.1.1
        int bits = Integer.valueOf(parts[1]);   // e.g. 16
        
        if (bits > 32 || bits < 0) {
            throw new IllegalArgumentException("Subnet mask must be in the [0,32] range");
        }
        
        address = new IpAddress(network);
        
        String str = address.getBits();   // e.g. 11000000 10101000 00000001 00000001
        
        // then apply a mask
        String sbase = str.substring(0, bits);  // e.g. 11000000 10101000
        
        String smin = sbase;
        String smax = sbase;
        
        for(int i = 0; i < 32 - bits; i++) {
            smin += "0";    // e.g. 11000000 10101000 00000000 00000000
            smax += "1";    // e.g. 11000000 10101000 11111111 11111111
        }

        long rest = 0;
        String rest_str = str.substring(bits);
        // If bits is equal to 32, we only have a single address subnet
        if (!rest_str.equals("")) {
            rest = Long.parseLong(str.substring(bits), 2); // e.g.  00000001 00000001
        }
        
        min = Long.parseLong(smin, 2) + rest;   // e.g. 192.168.1.1
        max = Long.parseLong(smax, 2);      // e.g. 192.168.255.255
        
        current = min;
    }
    
    /**
     * Indicates whether subnet has more addresses remaining.
     * 
     * @return boolean whether subnet has more addresses
     */
    public boolean hasMoreValues() {
        return current <= max;
    }
    
    /**
     * Generates next address from the subnet. Changes the current address.
     * 
     * @return String IPv4 address
     */
    public String nextValue() {
        if(current == max + 1)
            throw new IllegalStateException("No more values available in the scope.");
        
        return convertToIP(current++);
    }
    
    private String convertToIP(long val) {
        String res = "";

        int mask = 255 << 24;
        
        for(int i = 0; i < 4; i++) {
            int shift = 8 * (3 - i);

            int last = (int) ((val & mask) >> shift);
            mask = mask >>> 8;

            res += last + ".";
        }
        
        res = res.substring(0, res.lastIndexOf("."));
        
        return res;
    }
}
