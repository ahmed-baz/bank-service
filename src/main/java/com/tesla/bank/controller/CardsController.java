package com.tesla.bank.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.base.path}" + "cards")
public class CardsController {

    @GetMapping("/my-cards")
    public String getCards() {
        return "Get Cards";
    }
}
