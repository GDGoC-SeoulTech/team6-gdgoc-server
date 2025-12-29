package com.gdgoc.arcive.domain.activity.service;

import com.gdgoc.arcive.domain.activity.dto.ActivityResponse;
import com.gdgoc.arcive.domain.activity.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ActivityService {

    private final ActivityRepository activityRepository;

    @Cacheable(value = "activities", key = "'all'")
    public List<ActivityResponse> getAllActivities() {
        return activityRepository.findAll().stream()
                .map(ActivityResponse::from)
                .toList();
    }
}