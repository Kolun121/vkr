package ru.example.demo.controller;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.example.demo.domain.User;
import ru.example.demo.domain.enumeration.Role;
import ru.example.demo.service.SecurityService;
import ru.example.demo.service.UserService;

@Controller
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private SecurityService securityService;

    AuthController(
            UserService userService, 
            PasswordEncoder passwordEncoder,
            SecurityService securityService
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
        
    }
    
    @GetMapping("/registration")
    public String registration(Authentication authentication) {
        if(authentication != null){
            return "redirect:/";   
        }
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        User userFromDb = userService.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "auth/registration";
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
   
        user.setRole(Role.USER);
        userService.save(user);
        securityService.autoLogin(user.getUsername(), user.getPassword());

        return "redirect:/";
    }
    
    @GetMapping("/login")
    public String getLoginPage(Model model, Authentication authentication) {
        if(authentication != null){
            return "redirect:/";   
        }
        model.addAttribute("user", new User());
        return "auth/login";
    }
    
    
}
