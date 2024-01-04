package tutos.springsecuritytuto;

//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        try {
//            return User.builder()
//                    .username(username)
//                    .password("password")
//                    .roles("USER")
//                    .build();
//        } catch (Exception e) {
//            throw new UsernameNotFoundException("User not found");
//        }
//    }
//}
