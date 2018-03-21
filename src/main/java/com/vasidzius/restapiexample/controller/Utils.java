package com.vasidzius.restapiexample.controller;

import javax.servlet.http.HttpServletRequest;

public class Utils {
    public static String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        return authorization.substring(7);
    }

    public static boolean isAdmin(String token) {
        return "admin".equals(token);
    }

    public static boolean theSameAccount(long id, String token) {
        long parsedToken = Long.parseLong(token);
        return id == parsedToken;
    }

    public static boolean isValidToken(HttpServletRequest request, long customerId) {
        String token = getToken(request);
        return Utils.isAdmin(token) || Utils.theSameAccount(customerId, token);
    }
}
