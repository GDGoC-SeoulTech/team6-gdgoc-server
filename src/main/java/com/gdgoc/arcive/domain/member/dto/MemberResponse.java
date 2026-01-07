package com.gdgoc.arcive.domain.member.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponse {
    private Long id;
    private String name;
    private String email;
    private String studentId;
    private String major;
    private Integer generation;
    private String bio;
    private String profileImageUrl;
    private String role;
}