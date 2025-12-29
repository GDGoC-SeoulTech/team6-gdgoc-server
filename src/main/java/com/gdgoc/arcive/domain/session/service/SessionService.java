package com.gdgoc.arcive.domain.session.service;

import com.gdgoc.arcive.domain.session.dto.SessionResponse;
import com.gdgoc.arcive.domain.session.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SessionService {

    private final SessionRepository sessionRepository;

    @Cacheable(value = "sessions", key = "'all'")
    public List<SessionResponse> getAllSessions() {
        return sessionRepository.findAll().stream()
                .map(SessionResponse::from)
                .toList();
    }
}