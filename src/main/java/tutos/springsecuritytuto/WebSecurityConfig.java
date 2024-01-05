package tutos.springsecuritytuto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Value("${USER_PASSWORD}")
    private String userPassword;
    @Value("${USER_NAME}")
    private String userName;
    @Value("${USER_ROLES:}")
    private String[] userRoles;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/home")
                        .permitAll()
                        .requestMatchers("/secret")
                        .hasRole("SCHTROUMPF")
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(form -> form.loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // J'ai fait cette modif pour pouvoir accepter plusieurs roles pour un user. User.builder().roles() attend un String[] : on lui en fournit un oupas, s'il n'en a pas ou bien un vide il attribue "USER", sachant que derrière, ça transforme les roles en Granted Authorities =[ROLE_NOMDUROLE]
        User.UserBuilder userBuilder =
                User.builder()
                        .username(userName)
                        .password(passwordEncoder().encode(userPassword))
                        .roles((userRoles != null && userRoles.length > 0) ? userRoles : new String[]{"USER"});

        UserDetails user = userBuilder.build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // this one uses bcrypt
//        return new BCryptPasswordEncoder();
        // this one uses the current standard, whatever it is :
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
