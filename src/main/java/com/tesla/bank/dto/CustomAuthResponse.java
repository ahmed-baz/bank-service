package com.tesla.bank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.util.Date;


@Builder
public record CustomAuthResponse(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        Date responseDate,
        int status,
        String error,
        String message,
        String path) {
}
