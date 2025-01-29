package com.tesla.bank.controller;


import com.tesla.bank.dto.CustomerRequest;
import com.tesla.bank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody CustomerRequest request) {
        userService.registerCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("The customer is registered successfully");
    }
}
