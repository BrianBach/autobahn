package net.geant.autobahn.network;

import java.io.Serializable;

/**
 * 
 * @author jacek
 *
 */
public class Role implements Serializable {

	private static final long serialVersionUID = 7894108387294530386L;
	
	private Node node = null;
	private Link link = null;
	private boolean demarc;
	
	public Role() {
		
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public boolean isDemarc() {
		return demarc;
	}

	public void setDemarc(boolean demarc) {
		this.demarc = demarc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((node == null) ? 0 : node.hashCode());
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
		final Role other = (Role) obj;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		return true;
	}
}
