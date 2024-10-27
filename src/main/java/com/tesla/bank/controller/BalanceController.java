package com.tesla.bank.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.base.path}" + "balance")
public class BalanceController {

    @GetMapping("/my-balance")
    public String getBalance() {
        return "Get Balance";
    }
}
