package com.young.controller;

import com.young.domain.KsUser;
import com.young.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/toLogin")
    public ModelAndView toLogin(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/index");
    }

    @RequestMapping("/toRegister")
    public ModelAndView toRegister(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/register");
    }

    @RequestMapping("/loginOut")
    public ModelAndView loginOut(HttpServletRequest request, HttpServletResponse response) {
        if (null != request.getSession().getAttribute("young")) request.getSession().removeAttribute("young");
        return new ModelAndView("/index");
    }

    @RequestMapping("/login")
    @ResponseBody
    public Map login(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        map.put("code", 0);
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        KsUser user = userService.getUserByName(userName);
        if (null != user) {
            if (user.getLoginPwd().equals(password)) {
                request.getSession().setAttribute("young", user);
                if (1 == user.getRole()) map.put("jumpUrl", "/case/toList");
                else map.put("jumpUrl", "/case/toCase");
                map.put("code", 1);
            }
        }
        return map;
    }

    @RequestMapping("/register")
    @ResponseBody
    public Map register(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        map.put("code", 0);
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        KsUser user = userService.getUserByName(userName);
        if (null != user) {
            map.put("msg", "User already exists!");
        } else {
            userService.saveUser(userName, password);
            map.put("code", 1);
            map.put("jumpUrl", "/user/toLogin");
        }
        return map;
    }

}