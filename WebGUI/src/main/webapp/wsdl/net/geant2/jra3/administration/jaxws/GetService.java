
package net.geant2.jra3.administration.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by the CXF 2.0.3-incubator
 * Fri Apr 11 14:47:18 CEST 2008
 * Generated source version: 2.0.3-incubator
 * 
 */

@XmlRootElement(namespace = "http://administration.jra3.geant2.net/", name = "getService")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://administration.jra3.geant2.net/", name = "getService")

public class GetService {

@XmlElement(namespace = "", name = "arg0")
    private java.lang.String arg0;

    public java.lang.String getArg0 ()     {
	           return this.arg0;
        }

    public void setArg0 (   java.lang.String newArg0  )     {
	           this.arg0 = newArg0;
        }

}

