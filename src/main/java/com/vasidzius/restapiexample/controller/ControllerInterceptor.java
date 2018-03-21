package com.vasidzius.restapiexample.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ControllerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.contains("Bearer ")) {
            return true;
        }
        response.setStatus(401);
        return false;
    }
}
