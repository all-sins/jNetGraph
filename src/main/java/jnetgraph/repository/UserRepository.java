package jnetgraph.repository;

import jnetgraph.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
 public List<User> findByName(String name);
}
