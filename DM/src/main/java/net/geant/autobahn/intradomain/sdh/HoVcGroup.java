package net.geant.autobahn.intradomain.sdh;

import java.io.Serializable;

import net.geant.autobahn.intradomain.common.GenericConnection;

public class HoVcGroup implements Serializable {
	
	private static final long serialVersionUID = 4052099420695452360L;
	
	private GenericConnection hoVcGroup;
	private VlanTag vlanTag;
	private String name;
	
	public GenericConnection getHoVcGroup() {
		return hoVcGroup;
	}
	public void setHoVcGroup(GenericConnection hoVcGroup) {
		this.hoVcGroup = hoVcGroup;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public VlanTag getVlanTag() {
		return vlanTag;
	}
	public void setVlanTag(VlanTag vlanTag) {
		this.vlanTag = vlanTag;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((hoVcGroup == null) ? 0 : hoVcGroup.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final HoVcGroup other = (HoVcGroup) obj;
		if (hoVcGroup == null) {
			if (other.hoVcGroup != null)
				return false;
		} else if (!hoVcGroup.equals(other.hoVcGroup))
			return false;
		return true;
	}
}
