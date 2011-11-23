package net.geant.autobahn.aai;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserAuthParameters", namespace = "aai.autobahn.geant.net", propOrder = {
        "identifier", "organization", "projectMembership", "projectRole", "email" })
public class UserAuthParameters implements Serializable {

    private static final long serialVersionUID = 7565000373455342687L;
    
    public static final String PREFIX_ROLE = "ROLE_";
    public static final String PREFIX_EMAIL = "EMAIL_";
    public static final String PREFIX_PROJECTMEMB = "PM_";
    public static final String PREFIX_ORGANIZATION = "ORG_";
    
    @XmlTransient
    private long id;
    
    private String identifier = new String();
    private String organization = new String();
    private String projectMembership = new String();
    private String projectRole = new String();
    private String email = new String();

    public UserAuthParameters() {}
    
    public UserAuthParameters(String role, String email, String pm, String org) {
        this.projectRole = role;
        this.email = email;
        this.projectMembership = pm;
        this.organization = org;
    }

    public UserAuthParameters(String name, Set<String> authorities) {
    	this.setIdentifier(name);
    	
    	for(String authority: authorities) {
    		if(authority.startsWith(PREFIX_ROLE)) {
    			this.setProjectRole(authority.replaceFirst(PREFIX_ROLE, ""));
    		} else if(authority.startsWith(PREFIX_ORGANIZATION)) {
    			this.setOrganization(authority.replaceFirst(PREFIX_ORGANIZATION, ""));
    		} else if(authority.startsWith(PREFIX_PROJECTMEMB)) {
    			this.setProjectMembership(authority.replaceFirst(PREFIX_PROJECTMEMB, ""));
    		} else if(authority.startsWith(PREFIX_EMAIL)) {
                this.setEmail(authority.replaceFirst(PREFIX_EMAIL, ""));
            }
    	}
    }
    
    public String[] parametersToAuthorities() {
        String authorities[] = new String[4];
        authorities[0] = PREFIX_ROLE + getProjectRole();
        authorities[1] = PREFIX_ORGANIZATION + getOrganization();
        authorities[2] = PREFIX_PROJECTMEMB + getProjectMembership();
        authorities[3] = PREFIX_EMAIL + getEmail();

        return authorities;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier.trim();
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization.trim();
    }

    public String getProjectMembership() {
        return projectMembership;
    }

    public void setProjectMembership(String projectMembership) {
        this.projectMembership = projectMembership.trim();
    }

    public String getProjectRole() {
        return projectRole;
    }

    public void setProjectRole(String projectRole) {
        this.projectRole = projectRole.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result
                + ((identifier == null) ? 0 : identifier.hashCode());
        result = prime * result
                + ((organization == null) ? 0 : organization.hashCode());
        result = prime
                * result
                + ((projectMembership == null) ? 0 : projectMembership
                        .hashCode());
        result = prime * result
                + ((projectRole == null) ? 0 : projectRole.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserAuthParameters other = (UserAuthParameters) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (identifier == null) {
            if (other.identifier != null)
                return false;
        } else if (!identifier.equals(other.identifier))
            return false;
        if (organization == null) {
            if (other.organization != null)
                return false;
        } else if (!organization.equals(other.organization))
            return false;
        if (projectMembership == null) {
            if (other.projectMembership != null)
                return false;
        } else if (!projectMembership.equals(other.projectMembership))
            return false;
        if (projectRole == null) {
            if (other.projectRole != null)
                return false;
        } else if (!projectRole.equals(other.projectRole))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "UserAuthParameters [identifier=" + identifier
                + ", organization=" + organization + ", projectMembership="
                + projectMembership + ", projectRole=" + projectRole
                + ", email=" + email + "]";
    }
}
