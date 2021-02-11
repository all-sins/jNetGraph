package jnetgraph.mapper;
import jnetgraph.dto.UserDTO;
import jnetgraph.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail()
               );
    }

    public User fromDTO(UserDTO userDTO) {
        return new User(
                userDTO.getName(),
                userDTO.getSurname(),
                userDTO.getEmail()
                );
    }
}
