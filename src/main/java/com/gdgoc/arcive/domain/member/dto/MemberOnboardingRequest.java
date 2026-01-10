package com.gdgoc.arcive.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberOnboardingRequest {
    @NotBlank(message = "이름은 필수입니다.")
    private String name;
    
    @NotBlank(message = "학번은 필수입니다.")
    private String studentId;
    
    @NotBlank(message = "전공은 필수입니다.")
    private String major;
    
    @NotNull(message = "기수는 필수입니다.")
    @Positive(message = "기수는 양수여야 합니다.")
    private Integer generation;
}