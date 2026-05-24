package com.example.labo3.domain.services.impl;

import com.example.labo3.domain.dto.request.CreateSpecimenRequest;
import com.example.labo3.domain.dto.request.UpdateSpecimenRequest;
import com.example.labo3.domain.dto.response.pageable.PageableResponse;
import com.example.labo3.domain.dto.response.specimen.SpecimenResponse;
import com.example.labo3.domain.entities.Specimen;
import com.example.labo3.domain.exceptions.ResourceNotFoundException;
import com.example.labo3.domain.mappers.SpecimenMapper;
import com.example.labo3.domain.repositories.SpecimenRepository;
import com.example.labo3.domain.services.SpecimenService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecimenServiceImpl implements SpecimenService {

    private final SpecimenRepository specimenRepository;

    private final SpecimenMapper specimenMapper;

    @Override
    @Transactional
    public SpecimenResponse createSpecimen(
            CreateSpecimenRequest request
    ) {

        return specimenMapper.toDto(
                specimenRepository.save(
                        specimenMapper.toEntityCreate(request)
                )
        );
    }

    @Override
    public PageableResponse<SpecimenResponse> getAllSpecimens(
            int page,
            int size,
            String sortBy,
            String sortOrder
    ) {

        Sort sort = sortOrder.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Specimen> specimens =
                specimenRepository.findAll(pageable);

        Page<SpecimenResponse> responsePage =
                specimenMapper.toPageDto(specimens);

        return PageableResponse.<SpecimenResponse>builder()
                .content(responsePage.getContent())
                .page(responsePage.getNumber())
                .size(responsePage.getSize())
                .totalElements(responsePage.getTotalElements())
                .totalPages(responsePage.getTotalPages())
                .last(responsePage.isLast())
                .build();
    }

    @Override
    public SpecimenResponse getSpecimenById(UUID id) {

        return specimenMapper.toDto(
                specimenRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Specimen not found in Sheikah Slate records"
                                )
                        )
        );
    }

    @Override
    @Transactional
    public SpecimenResponse updateSpecimen(
            UUID id,
            UpdateSpecimenRequest request
    ) {

        this.getSpecimenById(id);

        return specimenMapper.toDto(
                specimenRepository.save(
                        specimenMapper.toEntityUpdate(request, id)
                )
        );
    }

    @Override
    @Transactional
    public SpecimenResponse deleteSpecimen(UUID id) {

        SpecimenResponse specimen =
                this.getSpecimenById(id);

        specimenRepository.deleteById(id);

        return specimen;
    }
}