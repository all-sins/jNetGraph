package jnetgraph.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
@Profile("local")
public class SecurityConfigLocal extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder encoder;

    @Autowired
    public SecurityConfigLocal(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("testuser@mail.com")
                .password(encoder.encode("root"))
                .authorities("ROLE_USER");

        auth.inMemoryAuthentication()
                .withUser("testadmin@mail.com")
                .password(encoder.encode("root"))
                .authorities("ROLE_ADMIN");

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
