package jnetgraph.mapper;

import jnetgraph.dto.UserDTO;
import jnetgraph.model.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserMapperTest {

    private UserMapper userMapper = new UserMapper();

    @Test
    public void toDTO() {


        User user = new User();
        user.setName("TestName");
        user.setSurname("TestSurname");
        user.setEmail("Test@Email.com");
        UserDTO userDTO = userMapper.toDTO(user);

        assertEquals("TestName", userDTO.getName());
        assertEquals("TestSurname", userDTO.getSurname());
        assertEquals("Test@Email.com", userDTO.getEmail());

    }

    @Test
    public void fromDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("TestName");
        userDTO.setSurname("TestSurname");
        userDTO.setEmail("Test@Email.com");
        User user = userMapper.fromDTO(userDTO);

        assertEquals("TestName", user.getName());
        assertEquals("TestSurname", user.getSurname());
        assertEquals("Test@Email.com", user.getEmail());
    }
}
