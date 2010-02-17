/*
 * User.java
 * 
 * 2006-02-13
 */
package net.geant.autobahn.reservation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.network.AdminDomain;

/**
 * This class represents user information in system.
 * 
 * @author <a href="mailto:radek.krzywania@man.poznan.pl">Radek Krzywania</a>
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="User", namespace="reservation.autobahn.geant.net", propOrder={
		"name", "homeDomain", "email"
})
public class User {
    
    /**
     * User name/identifier
     */
    private String name;
    
    /**
     * Identifier of user home domain.
     */
    private AdminDomain homeDomain;
    
    /**
     * E-mail address to use for notifications
     */
    private String email;
    
    /**
     * Parameterless constructor. 
     */
    public User() {
    }
    
    /**
     * Creates <code>User</code> object
     * @param name user name/identifier
     * @param homeDomain identifier of user home domain
     */
    public User(String name, String email, AdminDomain homeDomain) {
        this.name = name;
        this.homeDomain = homeDomain;
        this.email = email;
    }
    
    /**
     * @return Returns the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return Returns the homeDomain.
     */
    public AdminDomain getHomeDomain() {
        return homeDomain;
    }

    /**
     * @param homeDomain The homeDomain to set.
     */
    public void setHomeDomain(AdminDomain homeDomain) {
        this.homeDomain = homeDomain;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        
        if(obj == null || !(obj instanceof User))
            return false;
        
        User u2 = (User) obj;
        
        return getName().equals(u2.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
    
    @Override
    public String toString() {
        return "Name: " + name + ", home domain: " + 
                    homeDomain + ", email: " + email;
    }
}
