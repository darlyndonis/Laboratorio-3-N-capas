package com.example.labo3.domain.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralResponse<T> {
    private String message;
    private HttpStatus status;
    private T data;
    private LocalDateTime timestamp;
    private String path;
}