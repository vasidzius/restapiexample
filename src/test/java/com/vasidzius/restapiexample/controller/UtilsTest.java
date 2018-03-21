package com.vasidzius.restapiexample.controller;

import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class UtilsTest {

    @Test
    public void getToken() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class, Mockito.RETURNS_DEEP_STUBS);
        when(request.getHeader("Authorization")).thenReturn("Bearer 1");
        String token = Utils.getToken(request);
        assertEquals("1", token);
    }

}