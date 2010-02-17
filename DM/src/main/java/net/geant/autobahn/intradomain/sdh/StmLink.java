package net.geant.autobahn.intradomain.sdh;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.intradomain.common.GenericLink;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="StmLink", namespace="sdh.intradomain.autobahn.geant.net", propOrder={
		"stmLink", "och", "stmType", "status"
})
public class StmLink implements Serializable {
	
	private static final long serialVersionUID = 9068065845498798903L;
	
	private GenericLink stmLink;
	private Och och;
	private StmType stmType;
	private String status;
	
	public Och getOch() {
		return och;
	}
	public void setOch(Och och) {
		this.och = och;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public GenericLink getStmLink() {
		return stmLink;
	}
	public void setStmLink(GenericLink stmLink) {
		this.stmLink = stmLink;
	}
	public StmType getStmType() {
		return stmType;
	}
	public void setStmType(StmType stmType) {
		this.stmType = stmType;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stmLink == null) ? 0 : stmLink.hashCode());
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
		final StmLink other = (StmLink) obj;
		if (stmLink == null) {
			if (other.stmLink != null)
				return false;
		} else if (!stmLink.equals(other.stmLink))
			return false;
		return true;
	}
}
