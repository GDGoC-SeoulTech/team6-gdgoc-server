package com.gdgoc.arcive.domain.part.service;

import com.gdgoc.arcive.domain.part.dto.PartResponse;
import com.gdgoc.arcive.domain.part.repository.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PartService {

    private final PartRepository partRepository;

    @Cacheable(value = "parts", key = "'all'")
    public List<PartResponse> getAllParts() {
        return partRepository.findAll().stream()
                .map(PartResponse::from)
                .toList();
    }
}