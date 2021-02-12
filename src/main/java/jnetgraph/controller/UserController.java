package jnetgraph.controller;
import jnetgraph.dto.UserDTO;
import jnetgraph.mapper.UserMapper;
import jnetgraph.model.User;
import jnetgraph.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;


@RestController
@RequestMapping("/rest/api/User.svc")
public class UserController {

private final UserService userService;
private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/user")
    public UserDTO addNewUser(@Valid @RequestBody UserDTO userDTO) {
        User userToSave = userMapper.fromDTO(userDTO);
        User savedUser = userService.addNewUser(userToSave);
        return userMapper.toDTO(savedUser);
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/user({id})")
    public void softDeleteUser(@PathVariable("id") Long id){
        userService.softDeleteUser(id);
    }

    @RolesAllowed(("ROLE_ADMIN"))
    @PutMapping("/user({id})/changeemail/{email}")
    public void changeEmail(@PathVariable("id") Long id,
                            @PathVariable("email") String email){
        userService.changeEmail(id, email);

    }

    @RolesAllowed(("ROLE_USER"))
    @PutMapping("/user({id})/changepassword/{password}")
    public void changePassword(@PathVariable("id") Long id,
                            @PathVariable("password") String password){
        userService.changePassword(id, password);

    }

}
