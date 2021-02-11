package jnetgraph.service;

import jnetgraph.exception.UserAdministrationException;
import jnetgraph.model.User;
import jnetgraph.model.UserStatus;
import jnetgraph.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService userService = new UserService(userRepository);

    @Test
    public void findById() {
        User user = new User();
        user.setName("TestName");
        user.setSurname("TestSurname");
        user.setEmail("Test@Email.com");

        when(userRepository.findById(5L)).thenReturn(Optional.of(user));
        User result = userService.findById(5L);
        assertEquals(user, result);
    }

    @Test(expected = RuntimeException.class)
    public void findByIdThrowsError() {
        try {
            User user = new User();
            user.setName("TestName");
            user.setSurname("TestSurname");
            user.setEmail("Test@Email.com");
            when(userRepository.findById(5L)).thenReturn(Optional.of(user));
            userService.findById(4L);
        } catch (UserAdministrationException e) {
            String errorMessage = "No user found with given ID!";
            String errorCode = "401";
            assertEquals(errorMessage, e.getMessage());
            assertEquals(errorCode, e.getCode());
            throw e;
        }
    }

    @Test
    public void addNewUser() {
        User user = new User();
        user.setName("TestName");
        user.setSurname("TestSurname");
        user.setEmail("Test@Email.com");
        when(userRepository.save(user)).thenReturn(user);
        User result = userService.addNewUser(user);
        assertEquals(user, result);
        assertEquals(UserStatus.ACTIVE, user.getUserStatus());
    }

    @Test
    public void deleteUser() {
    //No need to test as only holds repository method
    }

    @Test
    public void softDeleteUser() {
        User user = new User();
        user.setId(5L);
        user.setName("TestName");
        user.setSurname("TestSurname");
        user.setEmail("Test@Email.com");
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(5L)).thenReturn(Optional.of(user));
        User result = userService.addNewUser(user);
        assertEquals(UserStatus.ACTIVE, result.getUserStatus());
        userService.softDeleteUser(user.getId());
        assertEquals(UserStatus.DELETED, result.getUserStatus());
    }
}
