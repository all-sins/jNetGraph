package jnetgraph.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("arturs")
                .password(passwordEncoder()
                        .encode("arturs1"))
                .authorities("ROLE_USER");

        auth.inMemoryAuthentication()
                .withUser("mara")
                .password(passwordEncoder()
                        .encode("mara1"))
                .authorities("ROLE_USER");

        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder()
                        .encode("admin1"))
                .authorities("ROLE_ADMIN");
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
                //TODO:Making Swagger and H2 accessible without authentification. remove for PROD
                .antMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/h2-console/**")
                .permitAll()
                .anyRequest()
                .authenticated().and()
                .httpBasic();
    }
}
