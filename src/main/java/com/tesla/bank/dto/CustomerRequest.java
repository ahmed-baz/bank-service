package com.tesla.bank.dto;


public record CustomerRequest(
        String firstName,
        String lastName,
        String email,
        String password) {
}
