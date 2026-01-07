package com.gdgoc.arcive.domain.member.service;

import com.gdgoc.arcive.domain.member.dto.MemberOnboardingRequest;
import com.gdgoc.arcive.domain.member.dto.MemberResponse;
import com.gdgoc.arcive.domain.member.dto.MemberUpdateRequest;
import com.gdgoc.arcive.domain.member.entity.Member;
import com.gdgoc.arcive.domain.member.entity.MemberProfile;
import com.gdgoc.arcive.domain.member.repository.MemberRepository;
import com.gdgoc.arcive.domain.member.repository.MemberProfileRepository;
import com.gdgoc.arcive.domain.project.repository.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final ProjectMemberRepository projectMemberRepository;

    @Transactional
    public void onboardMember(Long memberId, MemberOnboardingRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("MEMBER_NOT_FOUND"));

        MemberProfile profile = memberProfileRepository.findAll().stream()
                .filter(p -> p.getMember().getId().equals(memberId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("PROFILE_NOT_FOUND"));

        try {
            setField(profile, "name", request.getName());
            setField(profile, "studentId", request.getStudentId());
            setField(profile, "major", request.getMajor());
            setField(profile, "generation", request.getGeneration());
        } catch (Exception e) {
            throw new RuntimeException("필드 수정 중 오류 발생");
        }
    }

    public MemberResponse getMemberProfile(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("MEMBER_NOT_FOUND"));

        MemberProfile profile = memberProfileRepository.findAll().stream()
                .filter(p -> p.getMember().getId().equals(memberId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("PROFILE_NOT_FOUND"));

        return MemberResponse.builder()
                .id(member.getId())
                .name(profile.getName())
                .email(member.getEmail())
                .studentId(profile.getStudentId())
                .major(profile.getMajor().name())
                .generation(profile.getGeneration())
                .bio(profile.getBio())
                .profileImageUrl(profile.getProfileImageUrl())
                .role(member.getRole().name())
                .build();
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Transactional
    public void updateMemberProfile(Long memberId, MemberUpdateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("MEMBER_NOT_FOUND"));

        MemberProfile profile = memberProfileRepository.findAll().stream()
                .filter(p -> p.getMember().getId().equals(memberId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("PROFILE_NOT_FOUND"));

        try {
            setField(profile, "bio", request.getBio());
            setField(profile, "profileImageUrl", request.getProfileImageUrl());
        } catch (Exception e) {
            throw new RuntimeException("필드 수정 중 오류 발생");
        }
    }

    public List<MemberResponse> getMemberList(Integer generation, String part) {
        return memberProfileRepository.findAll().stream()
                .filter(profile -> {
                    boolean matchesGeneration = (generation == null) || profile.getGeneration() == generation;
                    boolean matchesPart = (part == null) || (profile.getMember().getRole().name().equalsIgnoreCase(part));
                    return matchesGeneration && matchesPart;
                })
                .map(profile -> {
                    Member member = profile.getMember();
                    return MemberResponse.builder()
                            .id(member.getId())
                            .name(profile.getName())
                            .email(member.getEmail())
                            .studentId(profile.getStudentId())
                            .major(profile.getMajor().name())
                            .generation(profile.getGeneration())
                            .bio(profile.getBio())
                            .profileImageUrl(profile.getProfileImageUrl())
                            .role(member.getRole().name())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<MemberResponse> searchMembersByName(String name) {
        return memberProfileRepository.findAll().stream()
                .filter(profile -> profile.getName().contains(name))
                .map(profile -> {
                    Member member = profile.getMember();
                    return MemberResponse.builder()
                            .id(member.getId())
                            .name(profile.getName())
                            .email(member.getEmail())
                            .studentId(profile.getStudentId())
                            .major(profile.getMajor().name())
                            .generation(profile.getGeneration())
                            .bio(profile.getBio())
                            .profileImageUrl(profile.getProfileImageUrl())
                            .role(member.getRole().name())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<String> getMemberProjects(Long userId) {
        return projectMemberRepository.findAll().stream()
                .filter(pm -> pm.getMember().getId().equals(userId))
                .map(pm -> pm.getProject().getProjectName())
                .collect(Collectors.toList());
    }

    public List<MemberResponse> getMyPartMembers(Long currentMemberId) {
        Member me = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new IllegalArgumentException("MEMBER_NOT_FOUND"));

        String myPart = me.getRole().name();

        return getMemberList(null, myPart);
    }
}