package ru.example.demo.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import ru.example.demo.domain.User;
import ru.example.demo.domain.enumeration.Role;
import ru.example.demo.exception.NotFoundException;
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
    public String getLoginPage(@RequestParam(value = "error", defaultValue = "false") boolean loginError, Model model, Authentication authentication) {
        if(authentication != null){
            return "redirect:/";   
        }
        model.addAttribute("user", new User());
        return "auth/login";
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){

//        log.error("Handling not found exception");
//        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
    
    @GetMapping("/403")
    public String get403Page(Model model, HttpServletRequest request) {
        String test = request.getRequestURI();
        model.addAttribute("test", test);
        return "auth/403error";
    }
    
    
}
