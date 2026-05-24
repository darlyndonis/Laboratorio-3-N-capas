package com.example.labo3.domain.controller;

import com.example.labo3.domain.dto.request.CreateSpecimenRequest;
import com.example.labo3.domain.dto.request.UpdateSpecimenRequest;
import com.example.labo3.domain.dto.response.GeneralResponse;
import com.example.labo3.domain.services.SpecimenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/specimens")
@RequiredArgsConstructor
public class SpecimenController {

    private final SpecimenService specimenService;

    @PostMapping
    public ResponseEntity<GeneralResponse<?>> createSpecimen(
            @Valid @RequestBody CreateSpecimenRequest request,
            HttpServletRequest servletRequest
    ) {

        return buildResponse(
                "Specimen created successfully",
                HttpStatus.CREATED,
                specimenService.createSpecimen(request),
                servletRequest.getRequestURI()
        );
    }

    @GetMapping
    public ResponseEntity<GeneralResponse<?>> getAllSpecimens(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "name")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String sortOrder,

            HttpServletRequest servletRequest
    ) {

        return buildResponse(
                "Specimens retrieved successfully",
                HttpStatus.OK,
                specimenService.getAllSpecimens(
                        page,
                        size,
                        sortBy,
                        sortOrder
                ),
                servletRequest.getRequestURI()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> getSpecimenById(
            @PathVariable UUID id,
            HttpServletRequest servletRequest
    ) {

        return buildResponse(
                "Specimen found",
                HttpStatus.OK,
                specimenService.getSpecimenById(id),
                servletRequest.getRequestURI()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> updateSpecimen(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateSpecimenRequest request,
            HttpServletRequest servletRequest
    ) {

        return buildResponse(
                "Specimen updated successfully",
                HttpStatus.OK,
                specimenService.updateSpecimen(id, request),
                servletRequest.getRequestURI()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse<?>> deleteSpecimen(
            @PathVariable UUID id,
            HttpServletRequest servletRequest
    ) {

        return buildResponse(
                "Specimen deleted successfully",
                HttpStatus.OK,
                specimenService.deleteSpecimen(id),
                servletRequest.getRequestURI()
        );
    }

    private ResponseEntity<GeneralResponse<?>> buildResponse(
            String message,
            HttpStatus status,
            Object data,
            String path
    ) {

        GeneralResponse<Object> response =
                GeneralResponse.builder()
                        .message(message)
                        .status(status)
                        .data(data)
                        .timestamp(LocalDateTime.now())
                        .path(path)
                        .build();

        return ResponseEntity
                .status(status)
                .body(response);
    }
}
