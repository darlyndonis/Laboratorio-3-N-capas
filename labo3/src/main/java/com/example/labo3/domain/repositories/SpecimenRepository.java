package com.example.labo3.domain.repositories;

import com.example.labo3.domain.entities.Specimen;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpecimenRepository
        extends JpaRepository<Specimen, UUID> {
}