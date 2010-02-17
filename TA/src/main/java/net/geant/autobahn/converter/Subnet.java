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
        String[] parts = subnet.split("\\/");
        
        String network = parts[0];
        int bits = Integer.valueOf(parts[1]);
        
        address = new IpAddress(network);
        
        String str = address.getBits();
        
        // then apply a mask
        String sbase = str.substring(0, bits);
        
        String smin = sbase;
        String smax = sbase;
        
        for(int i = 0; i < 32 - bits; i++) {
            smin += "0";
            smax += "1";
        }

        long rest = Long.parseLong(str.substring(bits), 2);
        
        min = Long.parseLong(smin, 2) + rest;
        max = Long.parseLong(smax, 2);
        
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
