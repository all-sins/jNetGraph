package jnetgraph.service;

import jnetgraph.exception.UserAdministrationException;
import jnetgraph.model.User;
import jnetgraph.model.UserStatus;
import jnetgraph.repository.RoleRepository;
import jnetgraph.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {
    private final UserRepository userRepository;
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final RoleRepository roleRepository;


    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User findById(Long id) {
        LOGGER.debug("Finding user with ID: " + id);
        return userRepository.findById(id).orElseThrow(
                () -> new UserAdministrationException("401", "No user found with given ID!"));
    }

    public User addNewUser(User user) {
        List<User> usersWithParticularEmail = userRepository.findByEmail(user.getEmail());
        if (usersWithParticularEmail.size()>0){
            throw new UserAdministrationException("405", "User with such e-mail address already exists.");
        }
        LOGGER.debug("Adding new user: " + user.getName() + " " + user.getSurname());
        user.setUserStatus(UserStatus.ACTIVE);
        return userRepository.save(user);
    }

    public void addRoleToUser(Long id, String role){
        User user = userRepository.findById(id).orElseThrow(()->new UserAdministrationException("401", "No user found with given ID!"));
        LOGGER.debug("Adding role " + role + " to user " + user.getName() + " " + user.getSurname());
        user.getRoles().add(roleRepository.findByName(role).get(0));
        userRepository.save(user);

    }

    public void softDeleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new UserAdministrationException("401", "No user found with given ID!"));
        user.setUserStatus(UserStatus.DELETED);
        LOGGER.debug("Setting user status DELETED to user "  + user.getName() + " " + user.getSurname());
        userRepository.save(user);

    }

    public User changePassword(User user){
        User userToChange = userRepository.findById(user.getId()).orElseThrow(()->new UserAdministrationException("401", "No user found with given ID!"));
        userToChange.setPassword(user.getPassword());
        LOGGER.debug("Changing password for user "  + user.getName() + " " + user.getSurname());
        return userRepository.save(userToChange);
    }

    public User changeEmail(User user){
        List<User> usersWithParticularEmail = userRepository.findByEmail(user.getEmail());
        if (usersWithParticularEmail.size()>0){
            throw new UserAdministrationException("405", "User with such e-mail address already exists.");
        }
        User userToChange = userRepository.findById(user.getId()).orElseThrow(()->new UserAdministrationException("401", "No user found with given ID!"));
        userToChange.setEmail(user.getEmail());
        LOGGER.debug("Changing e-mail for user "  + user.getName() + " " + user.getSurname());
        return userRepository.save(userToChange);
    }



}
