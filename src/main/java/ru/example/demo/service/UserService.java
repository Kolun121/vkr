package ru.example.demo.service;

import ru.example.demo.domain.User;

public interface UserService extends JpaService<User, Long> {
    User findByUsername(String username);
}
