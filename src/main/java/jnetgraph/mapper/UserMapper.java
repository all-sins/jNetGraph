package jnetgraph.mapper;

import jnetgraph.dto.UserDTO;
import jnetgraph.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword()
        );
    }

    public User fromDTO(UserDTO userDTO) {

        return new User(
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getSurname(),
                userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword())
        );

    }

}
