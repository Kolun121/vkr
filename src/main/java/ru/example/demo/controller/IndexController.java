package ru.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class IndexController {
    
    public IndexController() {
    }
    
    
    @GetMapping
    public String getIndexPage(Model model) {

        return "user/index";
    }

}
