package jnetgraph.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.client.RestTemplate;

//TODO: I don't understand what this annotation does. But without it Security config was not verifying user roles allowed/not allowed
@EnableGlobalMethodSecurity(jsr250Enabled=true, securedEnabled=true, prePostEnabled=true)
@Configuration
public class ApplicationConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
