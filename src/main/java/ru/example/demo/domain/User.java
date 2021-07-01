package ru.example.demo.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import ru.example.demo.domain.enumeration.Role;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "Значение должно быть заполнено")
    @Column(name = "username")
    private String username;
    
    @NotEmpty(message = "Значение должно быть заполнено")
    @Column(name = "password")
    private String password;
    
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;
}