package com.gdgoc.arcive.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberOnboardingRequest {
    private String name;
    private String studentId;
    private String major;
    private Integer generation;
}