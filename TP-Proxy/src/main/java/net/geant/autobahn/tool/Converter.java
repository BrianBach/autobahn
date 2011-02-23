package net.geant.autobahn.tool;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.geant.autobahn.intradomain.common.InterfaceType;
import net.geant.autobahn.intradomain.common.Location;
import net.geant.autobahn.intradomain.common.VersionInfo;
import net.geant.autobahn.tool.types.Constraint;
import net.geant.autobahn.tool.types.ConstraintsType;
import net.geant.autobahn.tool.types.GenericInterfaceType;
import net.geant.autobahn.tool.types.GenericLinkType;
import net.geant.autobahn.tool.types.IntradomainPathType;
import net.geant.autobahn.tool.types.NodeType;
import net.geant.autobahn.tool.types.ReservationParamsType;
import net.geant.autobahn.constraints.ConstraintsNames;

public class Converter {

	public static List<net.geant2.jra3.intradomain.common.GenericLink> convert(
	        IntradomainPathType par) {
	    if (par == null) {
	        return null;
	    }
	    
	    return convert(par.getLinks());
	}
	
    public static List<net.geant2.jra3.intradomain.common.GenericLink> convert(
            List<GenericLinkType> par) {
	
        if (par == null) {
            return null;
        }
        
	    List<net.geant2.jra3.intradomain.common.GenericLink> res = new
	        ArrayList<net.geant2.jra3.intradomain.common.GenericLink>();
	    
	    for (GenericLinkType linktype : par) {
	        res.add(convert(linktype));
	    }
	    
	    return res;    
	}
    
    public static net.geant2.jra3.intradomain.common.GenericLink convert(
            GenericLinkType par) {
        if (par == null) {
            return null;
        }
        
        net.geant2.jra3.intradomain.common.GenericLink res = new 
            net.geant2.jra3.intradomain.common.GenericLink();
        res.setDirection(par.getDirection());
        res.setEndInterface(convert(par.getEndInterface()));
        res.setStartInterface(convert(par.getStartInterface()));
        
        return res;
    }
    
    public static net.geant2.jra3.intradomain.common.GenericInterface convert(
            GenericInterfaceType par) {
        if (par == null) {
            return null;
        }
        
        net.geant2.jra3.intradomain.common.GenericInterface res = new 
            net.geant2.jra3.intradomain.common.GenericInterface();
        res.setBandwidth(par.getBandwidth());
        res.setDescription(par.getDescription());
        res.setDomainId(par.getDomainId());
        res.setName(par.getName());
        res.setMtu(Integer.toString(par.getMtu()));
        res.setStatus(par.getStatus());
        res.setInterfaceType(convert(par.getInterfaceType()));
        res.setNode(convert(par.getNode()));
        
        return res;
    }
    
    public static net.geant2.jra3.intradomain.common.InterfaceType convert(
            InterfaceType par) {
        if (par == null) {
            return null;
        }
        
        net.geant2.jra3.intradomain.common.InterfaceType res = new 
            net.geant2.jra3.intradomain.common.InterfaceType();
        res.setDataEncodingType(par.getDataEncodingType());
        res.setSwitchingType(par.getSwitchingType());
        
        return res;
    }

    public static net.geant2.jra3.intradomain.common.Node convert(
            NodeType par) {
        if (par == null) {
            return null;
        }
        
        net.geant2.jra3.intradomain.common.Node res = new 
            net.geant2.jra3.intradomain.common.Node();
        res.setDescription(par.getDescription());
        res.setIpAddress(par.getIpAddress());
        res.setLocation(convert(par.getLocation()));
        res.setModel(par.getModel());
        res.setName(par.getName());
        res.setOsName(par.getOsName());
        res.setOsVersion(par.getOsVersion());
        res.setStatus(par.getStatus());
        res.setVendor(par.getVendor());
        
        return res;
    }

    public static net.geant2.jra3.intradomain.common.Location convert(
            Location par) {
        if (par == null) {
            return null;
        }
        
        net.geant2.jra3.intradomain.common.Location res = new 
            net.geant2.jra3.intradomain.common.Location();
        res.setAltitude(par.getAltitude());
        res.setCabinet(par.getCabinet());
        res.setCountry(par.getCountry());
        res.setDescription(par.getDescription());
        res.setEMailAddress(par.geteMailAddress());
        res.setFloor(par.getFloor());
        res.setGeoLatitude(par.getGeoLatitude());
        res.setGeoLongitude(par.getGeoLongitude());
        res.setInstitution(par.getInstitution());
        res.setName(par.getName());
        res.setPhoneNumber(par.getPhoneNumber());
        res.setRoomSuite(par.getRoomSuite());
        res.setRow(par.getRow_());
        res.setStreet(par.getStreet());
        res.setType(par.getType());
        res.setZipCode(par.getZipCode());
        res.setZone(par.getZone());
        return res;
    }
    
    public static net.geant2.jra3.intradomain.common.VersionInfo convert(
            VersionInfo par) {
        if (par == null) {
            return null;
        }
        
        net.geant2.jra3.intradomain.common.VersionInfo res = new 
            net.geant2.jra3.intradomain.common.VersionInfo();
        res.setCreatedBy(par.getCreatedBy());
        res.setDateCreated(convert(par.getDateCreated()));
        res.setDateCreated(convert(par.getDateCreated()));
        res.setDateModified(convert(par.getDateModified()));
        res.setEndDate(convert(par.getEndDate()));
        res.setModifiedBy(par.getModifiedBy());
        res.setStartDate(convert(par.getStartDate()));
        return res;
    }
    
    public static net.geant2.jra3.reservation.ReservationParams convert(
            ReservationParamsType par_rsv, IntradomainPathType par_path) {
        if (par_rsv == null) {
            return null;
        }
        
        net.geant2.jra3.reservation.ReservationParams res = new 
            net.geant2.jra3.reservation.ReservationParams();
        
        res.setBidirectional(par_rsv.isBidirectional());
        res.setCapacity(par_rsv.getCapacity());
        res.setMaxDelay(par_rsv.getMaxDelay());
        res.setResiliency(par_rsv.getResiliency());
        res.setStartTime(convert(par_rsv.getStartTime()));
        res.setEndTime(convert(par_rsv.getEndTime()));
        
        // In the old TP interface, ReservationParams also contained constraints
        // These are now contained in the IntradomainPathType object, however they
        // are now "flat" (no range, minval, additive etc.)
        // Assumption is that we use the first constraint (ingress), named VLANS,
        // which we convert to a range constraint
        net.geant2.jra3.constraints.PathConstraints res_pathcon = 
            new net.geant2.jra3.constraints.PathConstraints();
        
        if (par_path != null && par_path.getIngressConstraints() != null
                && par_path.getIngressConstraints().getConstraints() != null) {
            for (Constraint con : par_path.getIngressConstraints().getConstraints()) {
                if (con != null && con.getName() != null && 
                        con.getName().equals(ConstraintsNames.VLANS)) {
                    net.geant2.jra3.constraints.RangeConstraint r = 
                        new net.geant2.jra3.constraints.RangeConstraint();
        
                    net.geant2.jra3.constraints.Range rng =
                        new net.geant2.jra3.constraints.Range();
                    rng.setMin(new Integer(con.getValue()));
                    rng.setMax(new Integer(con.getValue()));
                    r.getRanges().add(rng);
                    res_pathcon.getRangeConstraints().add(r);
                }
            }
        }
        
        res.setPathConstraints(res_pathcon);
                
        return res;
    }

    public static XMLGregorianCalendar convert(Date par) {
        if (par == null) {
            return null;
        }
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(par);
        XMLGregorianCalendar res = null;
        try {
            res = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static XMLGregorianCalendar convert(Calendar par) {
        if (par == null) {
            return null;
        }
        if (par instanceof GregorianCalendar) {
            XMLGregorianCalendar res = null;
            try {
                res = DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar)par);
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }
            return res;
        } else {
            return null;
        }
    }

}
