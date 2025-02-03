package com.tesla.bank.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tesla.bank.dto.CustomAuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.Date;

public class CustomBasicAuthEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setHeader("eazybank-error-reason", "Authentication failed");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        String message = authException != null && authException.getMessage() != null ? authException.getMessage() : "Unauthorized";
        CustomAuthResponse customAuthResponse = CustomAuthResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .responseDate(new Date())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase().toUpperCase())
                .message(message)
                .path(request.getRequestURI())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonResponse = objectMapper.writeValueAsString(customAuthResponse);
        response.getWriter().write(jsonResponse);
    }
}
