package net.geant.security.xml.userdetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.util.AuthorityUtils;

import net.geant.security.exception.DataFormatException;
import net.geant.security.xml.configurer.UserDetailsEditor;
import net.geant.security.xml.jaxb.beans.User;
import net.geant.autobahn.aai.UserAuthParameters;

public class XmlUserDetailsAdmin extends XmlUserDetailsManager {
    /*
     * Users list
     */
    private List<UserAuthParameters> usersParameters;
    
    public List<UserAuthParameters> getUsersParameters() {
        return usersParameters;
    }

    public XmlUserDetailsAdmin(String xmlFile) throws DataFormatException {
        try {            
            /*
             * Construct the userDetailsEditor for the given xml file
             */
            userDetailsEditor = UserDetailsEditor
                    .getUserDetailsEditor(xmlFile);

        } catch (DataFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
    }
    
    /*
     * Load the list of users from the xml file
     */
    public void loadUsers() {
        List<User> xmlUsers = userDetailsEditor.getUsers();
        
        usersParameters=new ArrayList<UserAuthParameters>();
        
        for(User user:xmlUsers) {
            /*
             * Convert UserDetails to UserAuthParameters
             */
            UserDetails userDetails = loadUserByUsername(user.getUSERNAME());
            Set<String> authorities = AuthorityUtils.authorityArrayToSet(userDetails.getAuthorities());
            
            UserAuthParameters auser = new UserAuthParameters(user.getUSERNAME(), authorities);
            usersParameters.add(auser);
        }
    }
    
    /*
     * Adds a user to the xml file, using username, organization, 
     * project role, project membership parameters
     */
    public void createUserFromUserParameters(String username, String organization, String projectMembership, String projectRole, String password) {
        UserAuthParameters user = new UserAuthParameters();
        
        /*
         * Initialize UserAuthParameters instance
         */
        user.setIdentifier(username);
        user.setOrganization(organization);
        user.setProjectMembership(projectMembership);
        user.setProjectRole(projectRole);
        
        try {
            /*
             * Convert password to MD5
             */
            byte[] bytesOfPassword;
            bytesOfPassword = password.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfPassword);

            password=new String(Hex.encodeHex(thedigest));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        /*
         * Convert organization, project role, project membership to authorities
         */
        GrantedAuthority[] grantedAuthorities = AuthorityUtils
                .stringArrayToAuthorityArray(user.parametersToAuthorities());

        /*
         * Create a User object and add it to the xml 
         * using the XmlUserDetailsManager.createUser function
         */
        org.springframework.security.userdetails.User newuser = new org.springframework.security.userdetails.User(
                user.getIdentifier(), password, true, true, true, true, grantedAuthorities);

        createUser(newuser);
    }
    
}
