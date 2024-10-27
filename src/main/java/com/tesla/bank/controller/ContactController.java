package com.tesla.bank.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.base.path}" + "contacts")
public class ContactController {

    @GetMapping
    public String sendMessage() {
        return "Message is sent successfully";
    }
}
