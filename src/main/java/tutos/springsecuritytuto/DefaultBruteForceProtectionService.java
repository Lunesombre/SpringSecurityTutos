package tutos.springsecuritytuto;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

// basé sur https://www.javadevjournal.com/spring-security/spring-security-brute-force-protection/
@Service("bruteForceProtectionService")
public class DefaultBruteForceProtectionService implements BruteForceProtectionService {

    private int maxFailedLogins;
    private int cacheMaxLimit;
    private final ConcurrentHashMap<String, FailedLogin> cache;

    private UserDetailsService userDetailsService;


    public DefaultBruteForceProtectionService() {
        this.cache = new ConcurrentHashMap<>(cacheMaxLimit);
    }

    private UserDetails getUser(final String username) {
        try {
            return userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return null; // Faudra mieux gérer si l'utilisateur n'est pas trouvé
        }
    }

    @Override
    public void registerLoginFailure(String username) {
        // adaptation du tuto pour se passer d'un User Repository
        User user = (User) getUser(username); // TODO: gérer le cas ou UserDetails n'est pas une instance de User
        if (user != null) {
            int failedCounter;
        }

    }

    @Override
    public void resetBruteForceCounter(String username) {

    }

    @Override
    public boolean isBruteForceAttack(String username) {
        return false;
    }

    public class FailedLogin {

        private int count;
        private LocalDateTime date;

        public FailedLogin() {
            this.count = 0;
            this.date = LocalDateTime.now();
        }

        public FailedLogin(int count, LocalDateTime date) {
            this.count = count;
            this.date = date;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }
    }
}

