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

    @RolesAllowed({"ADMIN","USER"})
    @PostMapping("/user")
    public UserDTO addNewUser(@Valid @RequestBody UserDTO userDTO) {
        User userToSave = userMapper.fromDTO(userDTO);
        User savedUser = userService.addNewUser(userToSave);
        return userMapper.toDTO(savedUser);
    }

    @RolesAllowed({"ADMIN"})
    @DeleteMapping("/user/delete({id})")
    public void softDeleteUser(@PathVariable("id") Long id){
        userService.softDeleteUser(id);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @PutMapping("/user/changeEmail")
    public UserDTO changeEmail(@Valid @RequestBody UserDTO userDTO){
        User userToSave = userMapper.fromDTO(userDTO);
        User savedUser = userService.changeEmail(userToSave);
        return userMapper.toDTO(savedUser);

    }

    @RolesAllowed({"USER"})
    @PutMapping("/user/changePassword")
    public UserDTO changePassword(@Valid @RequestBody UserDTO userDTO){
        User userToSave = userMapper.fromDTO(userDTO);
        User savedUser = userService.changePassword(userToSave);
        return userMapper.toDTO(savedUser);

    }
    @RolesAllowed({"ADMIN"})
    @PutMapping("/user({id})/addRole/({role})")
    public void addRoleToUser(@PathVariable("id") Long id, @PathVariable("role") String role){
        userService.addRoleToUser(id, role);

    }

}
