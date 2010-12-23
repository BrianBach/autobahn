package net.geant.autobahn.aai;

import java.util.Iterator;
import java.util.List;

import org.springframework.security.Authentication;
import org.springframework.security.ConfigAttribute;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.SecurityConfig;
import org.springframework.security.util.AuthorityUtils;
import org.springframework.security.vote.AccessDecisionVoter;

public class SpaceSeparatedBasedVoter implements AccessDecisionVoter {

    @Override
    public boolean supports(ConfigAttribute attribute) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean supports(Class clazz) {
        // TODO class check
        return true;
    }

    /**
     * Splits the config attribute by spaces.
     * Votes ACCESS_GRANTED if all tokens can be found in user authorities.
     * 
     * e.g. ROLE_USER ORG_RNET,ROLE_ADMINISTRATOR = (ROLE_USER and ORG_GRNET) or (ROLE_ADMINISTRATOR)
     */
    @Override
    public int vote(Authentication authentication, Object object,
        ConfigAttributeDefinition config) {
        /*
         * TODO: this maybe used to have access to the target object (DmUserAuthorizer)
        ReflectiveMethodInvocation cmi = (ReflectiveMethodInvocation)object;
        UserAuthorizer ua = (UserAuthorizer)cmi.getThis();
        */
        
        List<SecurityConfig> attributes=(List<SecurityConfig>) config.getConfigAttributes();
        Iterator<SecurityConfig> iterator = attributes.iterator();

        while ( iterator.hasNext() ) { 
            String roles[] = iterator.next().toString().split(" ");

            int i;
            for (i=0;i<roles.length;i++) {
                String role=roles[i];

                if(!AuthorityUtils.userHasAuthority(role)) {
                    break;
                }
            }
            
            if(i==roles.length) {
                return ACCESS_GRANTED;
            }
        }

        return ACCESS_DENIED;
    }

}
