
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

@XmlRootElement(namespace = "http://administration.jra3.geant2.net/", name = "getLogResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://administration.jra3.geant2.net/", name = "getLogResponse")

public class GetLogResponse {

@XmlElement(namespace = "", name = "log")
    private java.lang.String log;

    public java.lang.String getLog ()     {
	           return this.log;
        }

    public void setLog (   java.lang.String newLog  )     {
	           this.log = newLog;
        }

}

