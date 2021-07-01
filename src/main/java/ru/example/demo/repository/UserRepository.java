package ru.example.demo.repository;

import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.demo.domain.User;


public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
}
