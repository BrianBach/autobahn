package net.geant.autobahn.aai;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserAuthParameters", namespace = "aai.autobahn.geant.net", propOrder = {
        "identifier", "organization", "projectMembership", "projectRole"  })
public class UserAuthParameters implements Serializable {

    private static final long serialVersionUID = 7565000373455342687L;
    
    private String identifier;
    private String organization;
    private String projectMembership;
    private String projectRole;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getProjectMembership() {
        return projectMembership;
    }

    public void setProjectMembership(String projectMembership) {
        this.projectMembership = projectMembership;
    }

    public String getProjectRole() {
        return projectRole;
    }

    public void setProjectRole(String projectRole) {
        this.projectRole = projectRole;
    }

}
