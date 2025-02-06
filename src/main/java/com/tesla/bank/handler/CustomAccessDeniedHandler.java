package com.tesla.bank.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tesla.bank.dto.CustomAuthResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.Date;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setHeader("eazybank-denied-error-reason", "Authentication failed");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        String message = accessDeniedException != null && accessDeniedException.getMessage() != null ? accessDeniedException.getMessage() : "Your access is denied";
        CustomAuthResponse customAuthResponse = CustomAuthResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .responseDate(new Date())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase().toUpperCase())
                .message(message)
                .path(request.getRequestURI())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonResponse = objectMapper.writeValueAsString(customAuthResponse);
        response.getWriter().write(jsonResponse);
    }
}
