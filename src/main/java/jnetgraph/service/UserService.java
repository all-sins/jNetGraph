package jnetgraph.service;

import jnetgraph.exception.UserAdministrationException;
import jnetgraph.model.SpeedtestCLI;
import jnetgraph.model.TsuImpl;
import jnetgraph.model.User;
import jnetgraph.model.UserStatus;
import jnetgraph.repository.SpeedtestCLIRepository;
import jnetgraph.repository.TsuImplRepository;
import jnetgraph.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
                () -> new UserAdministrationException("401", "No user found with given ID!"));
    }

    public User addNewUser(User user) {
        LOGGER.debug("Adding new user: " + user.getName() + " " + user.getSurname());
        user.setUserStatus(UserStatus.ACTIVE);
        return userRepository.save(user);
    }


    public void softDeleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new UserAdministrationException("401", "No user found with given ID!"));
        user.setUserStatus(UserStatus.DELETED);
        userRepository.save(user);

    }



    public void changePassword(Long id, String newPassword){
        User user = userRepository.findById(id).orElseThrow(()->new UserAdministrationException("401", "No user found with given ID!"));
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public void changeEmail(Long id, String email){
        User user = userRepository.findById(id).orElseThrow(()->new UserAdministrationException("401", "No user found with given ID!"));
        user.setEmail(email);
        userRepository.save(user);
    }



}
