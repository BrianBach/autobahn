
package net.geant2.jra3.useraccesspoint.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by the CXF 2.0.3-incubator
 * Fri Apr 11 14:47:31 CEST 2008
 * Generated source version: 2.0.3-incubator
 * 
 */

@XmlRootElement(namespace = "http://useraccesspoint.jra3.geant2.net/", name = "submitService")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://useraccesspoint.jra3.geant2.net/", name = "submitService")

public class SubmitService {

@XmlElement(namespace = "", name = "request")
    private net.geant2.jra3.useraccesspoint.ServiceRequest request;

    public net.geant2.jra3.useraccesspoint.ServiceRequest getRequest ()     {
	           return this.request;
        }

    public void setRequest (   net.geant2.jra3.useraccesspoint.ServiceRequest newRequest  )     {
	           this.request = newRequest;
        }

}

