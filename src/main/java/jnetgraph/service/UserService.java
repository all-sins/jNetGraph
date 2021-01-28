package jnetgraph.service;

import jnetgraph.model.User;
import jnetgraph.model.UserStatus;
import jnetgraph.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    private final UserRepository userRepository;
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SpeedtestCLIService.class);


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        LOGGER.debug("Finding user with ID: " + id);
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No user found with given ID"));
    }

    public User addNewUser(User user) {
        LOGGER.debug("Adding new user: " + user.getName() + " " + user.getSurname());
        user.setUserStatus(UserStatus.ACTIVE);
        return userRepository.save(user);
    }
//
//    public void deleteUser(User user) {
//        userRepository.delete(user);
//    }

    public void softDeleteUser(User user) {
        user.setUserStatus(UserStatus.DELETED);
    }

}
