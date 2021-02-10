package jnetgraph.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jnetgraph.dto.UserDTO;
import jnetgraph.exception.UserAdministrationException;
import jnetgraph.model.User;
import jnetgraph.model.UserStatus;
import jnetgraph.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllegIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;


    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void addNewUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("TestName1");
        userDTO.setSurname("TestSurname1");
        userDTO.setEmail("abc@abc.com");

        mvc.perform(post("/rest/api/User.svc/user")
                .with(csrf()).param("action", "signup")

                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isOk());

        assertFalse(userRepository.findByName("TestName1").isEmpty());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void softDeleteUser() throws Exception {

        mvc.perform(delete("/rest/api/User.svc/user(2)").with(csrf()).param("action", "signup"));
        User user = userRepository.findById(2L).orElseThrow(() -> new UserAdministrationException("401", "No user found with given ID!"));
        assertTrue(user.getUserStatus().equals(UserStatus.DELETED));
    }
}


