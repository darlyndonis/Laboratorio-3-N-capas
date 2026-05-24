package com.example.labo3.domain.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {
    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;
    private String path;
}