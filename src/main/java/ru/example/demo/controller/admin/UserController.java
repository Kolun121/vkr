package ru.example.demo.controller.admin;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.example.demo.domain.User;
import ru.example.demo.domain.enumeration.Role;
import ru.example.demo.helper.BreadcrumbsListFactory;
import ru.example.demo.helper.objects.BreadcrumbsKind;
import ru.example.demo.helper.paging.Page;
import ru.example.demo.helper.paging.PagingRequest;
import ru.example.demo.service.UserService;

@Controller("adminUserController")
@RequestMapping("/admin/users")
public class UserController {
    private static final String ADMIN_USER_PATH = "admin/user";
    
    
    
    private final PasswordEncoder passwordEncoder;

    private final UserService userService;
    
    public UserController(
            UserService userService,
            PasswordEncoder passwordEncoder
            ) 
    {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    
    
    @GetMapping
    public String getProtectiveMeasuresPage(Model model){
        model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsList(BreadcrumbsKind.USERS));
        return ADMIN_USER_PATH + "/listUsers";
    }
    
    @PostMapping
    @ResponseBody
    public Page<User> listUsersAjax(Principal principal, @RequestBody PagingRequest pagingRequest) {
//        //Не выводим текущего пользователя
        Page<User> result = userService.findAllPagingRequest(pagingRequest);
        List<User> newData = result.getData().stream().filter(u -> !u.getUsername().equals(principal.getName())).collect(Collectors.toList());
        result.setData(newData);
       
        return result;
    } 
    
    @GetMapping("/new")
    public String municipalityCreatePage(Model model){
        model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsList(BreadcrumbsKind.USER_CREATE));
        
        model.addAttribute("roles", Role.values());
        model.addAttribute("user", new User());
        return ADMIN_USER_PATH + "/createUser";
    }
    
    @PostMapping("/new") 
    public String createUser(Model model, @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            
            model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsList(BreadcrumbsKind.USER_CREATE));
            model.addAttribute("roles", Role.values());
            model.addAttribute("user", user);
        
            return ADMIN_USER_PATH + "/createUser";
        } else {
            try{
                User userFromDb = userService.findByUsername(user.getUsername());
                if (userFromDb != null) {
                    return "redirect:/admin/users";
                }
            }catch(Exception e){
            }
            
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userService.save(user);

            return "redirect:/admin/users/" + savedUser.getId().toString();
        }
    }
    @GetMapping("{id}") 
    public String getUserById(Principal principal, @PathVariable Long id, Model model) {
        User user = userService.findById(id);
        
        try{
            User userFromDb = userService.findByUsername(user.getUsername());
            if (userFromDb.getUsername().equals(principal.getName())) {
                return "redirect:/admin/users";
            }
        }catch(Exception e){
        }

        long [] ids = new long[]{id};
        model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsListWithParams(BreadcrumbsKind.USER, ids));
       
        user.setPassword("");
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        
        return ADMIN_USER_PATH + "/updateUser";
    }
    
    @PostMapping("{id}")
    public String updateUserById(Principal principal, Model model, @PathVariable Long id, @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            long [] ids = new long[]{id};
            model.addAttribute("breadcrumbs", BreadcrumbsListFactory.getBreadcrumbsListWithParams(BreadcrumbsKind.USER, ids));
            
            model.addAttribute("roles", Role.values());
            return ADMIN_USER_PATH + "/updateUser";
        } else {
            user.setId(id);
            
            try{
                User userFromDb = userService.findByUsername(user.getUsername());
                if (
                        userFromDb.getUsername().equals(principal.getName()) && userFromDb.getId().equals(id)
                    ) {
                    return "redirect:/admin/users";
                }
            }catch(Exception e){
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userService.save(user);
            return "redirect:/admin/users/" + id;
        }
    }
    
    @DeleteMapping("/delete")
    @ResponseBody
    public void deleteUser(@RequestBody List<User> listUsers, Principal principal){
        
        userService.deleteAll(listUsers.stream().filter(u -> !u.getUsername().equals(principal.getName())).collect(Collectors.toList()));
    }
}
