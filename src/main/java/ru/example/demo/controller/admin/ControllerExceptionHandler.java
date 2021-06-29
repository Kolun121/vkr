package ru.example.demo.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import ru.example.demo.exception.NotFoundException;

@ControllerAdvice("adminControllerExceptionHandler")
public class ControllerExceptionHandler {
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("admin/404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView processMethodNotSupportedException(Exception exception) {

        ModelAndView modelAndView = new ModelAndView();
        
        modelAndView.setViewName("admin/404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
}
