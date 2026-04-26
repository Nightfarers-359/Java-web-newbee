package com.project.platform.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.project.platform.DTO.JWTpayload;
import com.project.platform.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            response.sendError(403, "Invalid token");
            return false;
        }
        token = token.substring(7);
        JWTpayload payload = jwtUtil.verifyToken(token);
        if (payload == null) {
            response.sendError(403, "Invalid token");
            return false;
        }
        //挂载payload
        request.setAttribute("JWTpayload", payload);
        return true;
    }
}
