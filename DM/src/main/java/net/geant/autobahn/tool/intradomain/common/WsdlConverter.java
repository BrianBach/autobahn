package net.geant.autobahn.tool.intradomain.common;

import java.util.ArrayList;
import java.util.List;

import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.GenericInterface;
import net.geant.autobahn.intradomain.common.InterfaceType;
import net.geant.autobahn.intradomain.common.Location;
import net.geant.autobahn.intradomain.common.Node;
import net.geant.autobahn.intradomain.common.VersionInfo;

/**
 * Purpose of this class is to convert object that are transferred over the DM-TP WS interface
 * from the new version (that includes id fields) to the old one that is still used by many
 * TPs and does not include id fields
 * 
 * Other changes:
 * GanericInterface.mtu was String, now is int
 * 
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 *
 */
public class WsdlConverter {

    static public List<net.geant.autobahn.tool.intradomain.common.GenericLink> convert(
            List<GenericLink> links) {

        if (links == null) {
            return null;
        }
        
        List<net.geant.autobahn.tool.intradomain.common.GenericLink> toolLinks = 
            new ArrayList<net.geant.autobahn.tool.intradomain.common.GenericLink>();
        
        for (GenericLink l : links) {
            net.geant.autobahn.tool.intradomain.common.GenericLink toolL = 
                new net.geant.autobahn.tool.intradomain.common.GenericLink();
            toolL.setDirection(l.getDirection());
            toolL.setEndInterface(convert(l.getEndInterface()));
            toolL.setPropDelay(l.getPropDelay());
            toolL.setProtection(l.getProtection());
            toolL.setStartInterface(convert(l.getStartInterface()));
            toolL.setVersion(convert(l.getVersion()));
            toolLinks.add(toolL);
        }
        return toolLinks;
    }
    
    static public net.geant.autobahn.tool.intradomain.common.GenericInterface convert(GenericInterface gif) {
        if (gif == null) {
            return null;
        }
        
        net.geant.autobahn.tool.intradomain.common.GenericInterface toolGif = 
            new net.geant.autobahn.tool.intradomain.common.GenericInterface();
        toolGif.setBandwidth(gif.getBandwidth());
        toolGif.setClientPort(gif.isClientPort());
        toolGif.setDescription(gif.getDescription());
        toolGif.setDomainId(gif.getDomainId());
        toolGif.setInterfaceType(convert(gif.getInterfaceType()));
        toolGif.setMtu(Integer.toString(gif.getMtu()));
        toolGif.setName(gif.getName());
        toolGif.setNode(convert(gif.getNode()));
        toolGif.setParentInterface(convert(gif.getParentInterface()));
        toolGif.setStatus(gif.getStatus());
        toolGif.setVersion(convert(gif.getVersion()));
        
        return toolGif;
    }

    static public net.geant.autobahn.tool.intradomain.common.InterfaceType convert(InterfaceType ifType) {
        if (ifType == null) {
            return null;
        }
        
        net.geant.autobahn.tool.intradomain.common.InterfaceType toolIfType = 
            new net.geant.autobahn.tool.intradomain.common.InterfaceType();
        toolIfType.setDataEncodingType(ifType.getDataEncodingType());
        toolIfType.setSwitchingType(ifType.getSwitchingType());
        
        return toolIfType;
    }

    static public net.geant.autobahn.tool.intradomain.common.Node convert(Node n) {
        if (n == null) {
            return null;
        }
        
        net.geant.autobahn.tool.intradomain.common.Node toolN = 
            new net.geant.autobahn.tool.intradomain.common.Node();
        toolN.setDescription(n.getDescription());
        toolN.setIpAddress(n.getIpAddress());
        toolN.setLocation(convert(n.getLocation()));
        toolN.setModel(n.getModel());
        toolN.setName(n.getName());
        toolN.setOsName(n.getOsName());
        toolN.setOsVersion(n.getOsVersion());
        toolN.setStatus(n.getStatus());
        toolN.setVendor(n.getVendor());
        toolN.setVersion(convert(n.getVersion()));
        
        return toolN;
    }

    static public net.geant.autobahn.tool.intradomain.common.VersionInfo convert(VersionInfo vi) {
        if (vi == null) {
            return null;
        }
        
        net.geant.autobahn.tool.intradomain.common.VersionInfo toolVi = 
            new net.geant.autobahn.tool.intradomain.common.VersionInfo();
        toolVi.setCreatedBy(vi.getCreatedBy());
        toolVi.setDateCreated(vi.getDateCreated());
        toolVi.setDateModified(vi.getDateModified());
        toolVi.setEndDate(vi.getEndDate());
        toolVi.setModifiedBy(vi.getModifiedBy());
        toolVi.setStartDate(vi.getStartDate());
        
        return toolVi;
    }

    static public net.geant.autobahn.tool.intradomain.common.Location convert(Location l) {
        if (l == null) {
            return null;
        }
        
        net.geant.autobahn.tool.intradomain.common.Location toolL = 
            new net.geant.autobahn.tool.intradomain.common.Location();
        toolL.setAltitude(l.getAltitude());
        toolL.setCabinet(l.getCabinet());
        toolL.setCountry(l.getCountry());
        toolL.setDescription(l.getDescription());
        toolL.seteMailAddress(l.geteMailAddress());
        toolL.setFloor(l.getFloor());
        toolL.setGeoLatitude(l.getGeoLatitude());
        toolL.setGeoLongitude(l.getGeoLongitude());
        toolL.setInstitution(l.getInstitution());
        toolL.setName(l.getName());
        toolL.setPhoneNumber(l.getPhoneNumber());
        toolL.setRoomSuite(l.getRoomSuite());
        toolL.setRow_(l.getRow_());
        toolL.setStreet(l.getStreet());
        toolL.setType(l.getType());
        toolL.setZipCode(l.getZipCode());
        toolL.setZone(l.getZone());
        
        return toolL;
    }
    
}
