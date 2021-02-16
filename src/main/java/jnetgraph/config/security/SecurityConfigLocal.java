package jnetgraph.config.security;

import jnetgraph.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
@Component
@Profile("local")
public class SecurityConfigLocal extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final JNetGraphUserDetailsService userDetailsService;
    private final PasswordEncoder encoder;


    @Autowired
    public SecurityConfigLocal(UserRepository userRepository, JNetGraphUserDetailsService userDetailsService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }


    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder);
        return authProvider;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {



        // Disabled build in protection against CORS.
        http.csrf().disable();

        //TODO:This is only to work with H2 console. For PROD should be removed
        http.headers().frameOptions().disable();

        http.authorizeRequests()
                //TODO:Making Swagger and H2 accessible without authentification. Remove for PROD
                .antMatchers("/rest/api/User.svc/user", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/h2-console/**")
                .permitAll()

                .anyRequest()
                .authenticated().and()
                .httpBasic();
    }

}


