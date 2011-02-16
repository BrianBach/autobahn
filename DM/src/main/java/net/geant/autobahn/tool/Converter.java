package net.geant.autobahn.tool;

import java.util.ArrayList;
import java.util.List;

import net.geant.autobahn.constraints.AdditiveConstraint;
import net.geant.autobahn.constraints.BooleanConstraint;
import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.MinValueConstraint;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.common.GenericInterface;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.Node;
import net.geant.autobahn.reservation.ReservationParams;
import net.geant.autobahn.tool.types.Constraint;
import net.geant.autobahn.tool.types.ConstraintsType;
import net.geant.autobahn.tool.types.GenericInterfaceType;
import net.geant.autobahn.tool.types.GenericLinkType;
import net.geant.autobahn.tool.types.IntradomainPathType;
import net.geant.autobahn.tool.types.NodeType;
import net.geant.autobahn.tool.types.ReservationParamsType;

public class Converter {

	public static ReservationParamsType convert(ReservationParams par) {
		ReservationParamsType res = new ReservationParamsType();
		
		res.setBidirectional(par.isBidirectional());
		res.setCapacity(par.getCapacity());
		res.setMaxDelay(par.getMaxDelay());
		res.setResiliency(par.getResiliency());
		res.setStartTime(par.getStartTime());
		res.setEndTime(par.getEndTime());
		
		return res;
	}
	
	public static IntradomainPathType convert(IntradomainPath ipath) {
		IntradomainPathType res = new IntradomainPathType();
		List<GenericLinkType> links = new ArrayList<GenericLinkType>();
		List<ConstraintsType> cons = new ArrayList<ConstraintsType>();
		
		for(GenericLink gl : ipath.getLinks()) {
			links.add(convert(gl));
			cons.add(convert(ipath.getConstraints(gl)));
		}
		
		res.setLinks(links);
		res.setConstraints(cons);
		
		return res;
	}
	
	public static GenericLinkType convert(GenericLink gl) {
		GenericLinkType res = new GenericLinkType();
		
		res.setDirection(gl.getDirection());
		res.setStartInterface(convert(gl.getStartInterface()));
		res.setEndInterface(convert(gl.getEndInterface()));
		
		return res;
	}
	
	public static GenericInterfaceType convert(GenericInterface gi) {
		GenericInterfaceType res = new GenericInterfaceType();
		
		res.setBandwidth(gi.getBandwidth());
		res.setDescription(gi.getDescription());
		res.setDomainId(gi.getDomainId());
		res.setName(gi.getName());
		res.setMtu(gi.getMtu());
		res.setStatus(gi.getStatus());
		res.setInterfaceType(gi.getInterfaceType());
		res.setNode(convert(gi.getNode()));
		
		return res;
	}
	
	public static NodeType convert(Node n) {
		NodeType res = new NodeType();
		
		res.setDescription(n.getDescription());
		res.setIpAddress(n.getIpAddress());
		res.setLocation(n.getLocation());
		res.setModel(n.getModel());
		res.setName(n.getName());
		res.setOsName(n.getOsName());
		res.setOsVersion(n.getOsVersion());
		res.setStatus(n.getStatus());
		res.setVendor(n.getVendor());
		res.setVlanTranslationSupport(n.isVlanTranslationSupport());
		
		return res;
	}
	
	public static ConstraintsType convert(PathConstraints pcon) {
		ConstraintsType res = new ConstraintsType();
		List<Constraint> cons = new ArrayList<Constraint>();
		
		for(ConstraintsNames rcon_name : pcon.getRangeNames()) {
			RangeConstraint rcon = pcon.getRangeConstraint(rcon_name);
			cons.add(new Constraint(rcon_name, "" + rcon.getFirstValue()));
		}
		
		for(ConstraintsNames acon_name : pcon.getAddNames()) {
			AdditiveConstraint acon = pcon.getAdditiveConstraint(acon_name);
			cons.add(new Constraint(acon_name, "" + acon.getValue()));
		}
		
		for(ConstraintsNames mvcon_name : pcon.getMinValNames()) {
			MinValueConstraint mvcon = pcon.getMinValueConstraint(mvcon_name);
			cons.add(new Constraint(mvcon_name, "" + mvcon.getValue()));
		}

		for(ConstraintsNames bcon_name : pcon.getBoolNames()) {
			BooleanConstraint bcon = pcon.getBooleanConstraint(bcon_name);
			cons.add(new Constraint(bcon_name, "" + bcon.getValue()));
		}

		res.setConstraints(cons);
		
		return res;
	}
}
