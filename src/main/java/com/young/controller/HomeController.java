package com.young.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @RequestMapping("home")
    public ModelAndView toSuccess(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/index");
    }
}
