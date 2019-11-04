package com.young.common;

import com.young.domain.KsUser;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MarpossSessionInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        KsUser user = (KsUser) httpServletRequest.getSession().getAttribute("young");
        if (null != user && 1 == user.getRole()) {
            String path = httpServletRequest.getContextPath() + "/user/toLogin";
            httpServletResponse.sendRedirect(path);
            return false;
        }
        return true;
    }
}
