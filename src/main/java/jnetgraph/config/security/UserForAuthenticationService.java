package jnetgraph.config.security;

import jnetgraph.model.User;
import jnetgraph.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

//UserForAuthentication service class. Implements UserDetailsService and has only one method - loadUserByUsername.
//It finds user based on provided user name and maps it to UserForAuthentication.

@Component
public class UserForAuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserForAuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return new UserForAuthentication(user);

    }


}
