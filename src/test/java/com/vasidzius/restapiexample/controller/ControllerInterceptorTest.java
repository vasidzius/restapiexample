package com.vasidzius.restapiexample.controller;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ControllerInterceptorTest {

    @Test
    public void success() {
        ControllerInterceptor interceptor = new ControllerInterceptor();
        HttpServletRequest request = mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object handler = new Object();
        when(request.getHeader("Authorization")).thenReturn("Bearer admin");
        assertTrue(interceptor.preHandle(request, response, handler));
    }

    @Test
    public void failed1() {
        ControllerInterceptor interceptor = new ControllerInterceptor();
        HttpServletRequest request = mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object handler = new Object();
        when(request.getHeader("Authorization")).thenReturn(null);
        assertFalse(interceptor.preHandle(request, response, handler));
    }

    @Test
    public void failed2() {
        ControllerInterceptor interceptor = new ControllerInterceptor();
        HttpServletRequest request = mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object handler = new Object();
        when(request.getHeader("Authorization")).thenReturn("abrakadabra");
        assertFalse(interceptor.preHandle(request, response, handler));
    }

}