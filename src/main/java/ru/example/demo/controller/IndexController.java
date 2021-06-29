package ru.example.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import ru.example.demo.exception.NotFoundException;



@Controller
public class IndexController {
    
    public IndexController() {
    }
    
    
    @GetMapping
    public String getIndexPage(Model model) {

        return "user/index";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("admin/404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
}
