package jnetgraph.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jnetgraph.dto.UserDTO;
import jnetgraph.model.User;
import jnetgraph.model.UserStatus;
import jnetgraph.repository.UserRepository;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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


    @Test
    public void addNewUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("TestName1");
        userDTO.setSurname("TestSurname1");
        userDTO.setEmail("abc@abc.com");

        mvc.perform(post("/rest/api/User.svc/user")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isOk());

        assertFalse(userRepository.findByName("TestName1").isEmpty());
    }

    @Test
    public void softDeleteUser() throws Exception {
        mvc.perform(delete("/rest/api/User.svc/user(2)"));
        User user = userRepository.findById(2L).orElseThrow(() -> new RuntimeException("No such user!"));
        assertTrue(user.getUserStatus().equals(UserStatus.DELETED));
    }
}
