
package net.geant2.jra3.gui.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by the CXF 2.0.3-incubator
 * Fri Apr 11 14:47:26 CEST 2008
 * Generated source version: 2.0.3-incubator
 * 
 */

@XmlRootElement(namespace = "http://gui.jra3.geant2.net/", name = "statusUpdated")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://gui.jra3.geant2.net/", name = "statusUpdated")

public class StatusUpdated {

@XmlElement(namespace = "", name = "idm")
    private java.lang.String idm;
@XmlElement(namespace = "", name = "status")
    private net.geant2.jra3.administration.Status status;

    public java.lang.String getIdm ()     {
	           return this.idm;
        }

    public void setIdm (   java.lang.String newIdm  )     {
	           this.idm = newIdm;
        }

    public net.geant2.jra3.administration.Status getStatus ()     {
	           return this.status;
        }

    public void setStatus (   net.geant2.jra3.administration.Status newStatus  )     {
	           this.status = newStatus;
        }

}

