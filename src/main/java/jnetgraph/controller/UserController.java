package jnetgraph.controller;
import jnetgraph.dto.UserDTO;
import jnetgraph.mapper.UserMapper;
import jnetgraph.model.User;
import jnetgraph.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/user")
    public UserDTO addNewUser(@Valid @RequestBody UserDTO userDTO) {
        User userToSave = userMapper.fromDTO(userDTO);
        User savedUser = userService.addNewUser(userToSave);
        return userMapper.toDTO(savedUser);
    }


    @DeleteMapping("/user({id})")
    public void hardDeleteUser(@PathVariable("id") Long id){
        User userToDelete = userService.findById(id);
        userService.deleteUser(userToDelete);
    }
}
