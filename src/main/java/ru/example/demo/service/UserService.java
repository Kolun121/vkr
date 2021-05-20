package ru.example.demo.service;

import ru.example.demo.domain.User;

public interface UserService extends CrudService<User, Long> {
    User findByUsername(String username);
}
