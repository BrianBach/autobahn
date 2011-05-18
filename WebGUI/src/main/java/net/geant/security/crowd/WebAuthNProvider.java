package net.geant.security.crowd;

import javax.naming.NamingException;

import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.util.AuthorityUtils;

import com.atlassian.crowd.integration.http.HttpAuthenticator;
import com.atlassian.crowd.integration.service.AuthenticationManager;
import com.atlassian.crowd.integration.springsecurity.user.CrowdUserDetails;
import com.atlassian.crowd.integration.springsecurity.user.CrowdUserDetailsService;

public class WebAuthNProvider extends AuthNProvider {

    public WebAuthNProvider(AuthenticationManager authenticationManager,
            HttpAuthenticator httpAuthenticator,
            CrowdUserDetailsService userDetailsService) {
        super(authenticationManager, httpAuthenticator, userDetailsService);
        // TODO Auto-generated constructor stub
    }
    
    GrantedAuthority[] attributesToAuthorities(AuthNToken authNToken) {
        CrowdUserDetails crowdUserDetails = (CrowdUserDetails) authNToken.getPrincipal();
                
        String authorities[]=new String[4];
        try {
            String role = new String("ROLE_" + ((String[])authNToken.getAttributes().get("projectRole").get())[0]);
            String membership = new String("PM_" + ((String[])authNToken.getAttributes().get("projectMembership").get())[0]);
            String organization = new String("ORG_" + ((String[])authNToken.getAttributes().get("organization").get())[0]);
            String email = new String("EMAIL_" + crowdUserDetails.getEmail());
            
            authorities[0]=role;
            authorities[1]=membership;
            authorities[2]=organization;
            authorities[3]=email;
            
            for(int i=0;i<authorities.length;i++) {
                System.out.println(authorities[i]);
            }
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return AuthorityUtils.stringArrayToAuthorityArray(authorities);
   }
    
    public Authentication authenticate(Authentication authentication) {
        
        AuthNToken authNToken = new AuthNToken(super
                .authenticate(authentication));
        if (authNToken.isAuthenticated()) {
            //logger.info("Setting attributes for authenticated user: " + authNToken.getName());
            authNToken.setAttributes();
            
            Authentication newAuthentication;
            
            //GrantedAuthorityImpl authority = new GrantedAuthorityImpl("ROLE_NEWROLE");
            newAuthentication=new UsernamePasswordAuthenticationToken(
                    authNToken.getPrincipal(),
                    authNToken.getCredentials(),
                    attributesToAuthorities(authNToken));
            
            System.out.println("ISAUTHD: " + newAuthentication.isAuthenticated());
            return new AuthNToken(newAuthentication);
        }
        
        return authNToken;
    }
}
