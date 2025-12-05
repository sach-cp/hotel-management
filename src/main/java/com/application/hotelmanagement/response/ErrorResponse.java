package com.application.hotelmanagement.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Data
@Builder
public class ErrorResponse {
    private LocalDate timestamp;
    private HttpStatus status;
    private String error;
    private String message;
}
