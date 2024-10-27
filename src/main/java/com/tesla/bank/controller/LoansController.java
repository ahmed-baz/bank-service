package com.tesla.bank.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.base.path}" + "loans")
public class LoansController {

    @GetMapping("/my-loans")
    public String getLoans() {
        return "Get Loans";
    }
}
