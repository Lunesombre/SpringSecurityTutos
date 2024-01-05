package tutos.springsecuritytuto;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * À chaque authentification qui réussit ou échoue, Spring security publie une AuthenticationSuccessEvent ou un AuthenticationFailureEvent.
 * On va les récupérer.<br/>
 * Ici, on écoute les AuthenticationFailureEvent.<br/>
 * Basé sur https://www.javadevjournal.com/spring-security/spring-security-brute-force-protection/
 */
@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private static Logger LOG = LoggerFactory.getLogger(AuthenticationFailureListener.class);

    @Resource(name = "bruteForceProtectionService")
    private BruteForceProtectionService bruteForceProtectionService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String username = event.getAuthentication().getName();
        LOG.info("********** login failed for user {} ", username);
        bruteForceProtectionService.registerLoginFailure(username);

    }

}
