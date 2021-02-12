package jnetgraph.config.security;

import jnetgraph.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
@Component
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;

    @Autowired
    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("mara@mail.com")
                .password(passwordEncoder().encode(userRepository.findByEmail("mara@mail.com").get(0).getPassword()))
                .authorities("ROLE_USER");

        auth.inMemoryAuthentication()
                .withUser("admin@mail.com")
                .password(passwordEncoder().encode("root"))
                .authorities("ROLE_ADMIN");
                // userRepository.findByEmail("admin@mail.com").get(0).getPassword())
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        //TODO:This is only to work with H2 console. For PROD should be removed
        http.headers().frameOptions().disable();

        http.authorizeRequests()
                //TODO:Making Swagger and H2 accessible without authentification. Remove for PROD
                .antMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/h2-console/**")
                .permitAll()

                .anyRequest()
                .authenticated().and()
                .httpBasic();
    }
}
