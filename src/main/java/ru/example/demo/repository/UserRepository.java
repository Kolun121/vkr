package ru.example.demo.repository;

import java.util.Optional;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import ru.example.demo.domain.User;


public interface UserRepository extends CrudRepository<User, Long>{
    Optional<User> findByUsername(String username);
    Set<User> findAll();
}
