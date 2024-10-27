package com.tesla.bank.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.base.path}" + "accounts")
public class AccountController {

    @GetMapping("/my-accounts")
    public String getAccount() {
        return "Get Account Data";
    }
}
