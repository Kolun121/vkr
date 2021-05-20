package ru.example.demo.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("adminIndexController")
@RequestMapping("/admin")
public class IndexController {
    
    public IndexController() {
    }
    
    
    @GetMapping
    public String getIndexPage(Model model) {

        return "admin/index";
    }

}
