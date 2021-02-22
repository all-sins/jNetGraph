package jnetgraph.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

//@EnableSecurity already "implements" @Configuration so I am not adding it anymore
@EnableWebSecurity
@Profile("prod")
public class SecurityConfigProd extends WebSecurityConfigurerAdapter {


    private final UserForAuthenticationService userDetailsService;
    private final PasswordEncoder encoder;

    @Autowired
    public SecurityConfigProd(UserForAuthenticationService userDetailsService, PasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    //Data Access Object authentication provider that is created with our UserForAuthenticationService.
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder);
        return authProvider;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Disabled build in protection against CORS.
        http.csrf().disable();

        //TODO:This is only to work with H2 console. For PROD should be removed
        http.headers().frameOptions().disable();

        http.authorizeRequests()
                //TODO:Making Swagger and H2 accessible without authentification. Remove for PROD
                .antMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/h2-console/**")
                .permitAll()
                .anyRequest().authenticated().and().httpBasic();
    }

}


