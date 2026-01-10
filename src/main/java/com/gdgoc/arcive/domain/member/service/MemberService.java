package com.gdgoc.arcive.domain.member.service;

import com.gdgoc.arcive.domain.member.dto.*;
import com.gdgoc.arcive.domain.member.entity.Member;
import com.gdgoc.arcive.domain.member.entity.MemberProfile;
import com.gdgoc.arcive.domain.member.exception.MemberErrorCode;
import com.gdgoc.arcive.domain.member.exception.MemberException;
import com.gdgoc.arcive.domain.member.repository.MemberRepository;
import com.gdgoc.arcive.domain.member.repository.MemberProfileRepository;
import com.gdgoc.arcive.domain.project.repository.ProjectMemberRepository;
import com.gdgoc.arcive.infra.s3.config.S3Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final S3Properties s3Properties;

    @Transactional
    public void onboardMember(Long memberId, MemberOnboardingRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        MemberProfile profile = memberProfileRepository.findByMemberIdWithMember(memberId)
                .orElse(null);

        if (profile == null) {
            // MemberProfile이 없으면 생성 (첫 온보딩)
            String defaultProfileImageUrl = getDefaultProfileImageUrl();
            profile = MemberProfile.create(
                    member,
                    request.getName(),
                    request.getStudentId(),
                    request.getMajor(),
                    request.getGeneration(),
                    defaultProfileImageUrl
            );
            memberProfileRepository.save(profile);
        } else {
            // MemberProfile이 있으면 업데이트
            profile.updateOnboardingInfo(
                    request.getName(),
                    request.getStudentId(),
                    request.getMajor(),
                    request.getGeneration()
            );
        }
    }

    @Transactional
    public void updateMemberProfile(Long memberId, MemberUpdateRequest request) {
        MemberProfile profile = findProfileByMemberIdOptimized(memberId);

        profile.updateProfile(request.getBio(), request.getProfileImageUrl());
    }

    public MemberDetailResponse getMemberProfile(Long memberId) {
        MemberProfile profile = findProfileByMemberIdOptimized(memberId);
        return convertToDetailResponse(profile);
    }

    public List<MemberSummaryResponse> getMemberList(Integer generation, String part) {

        return memberProfileRepository.findByGenerationAndPart(generation, part).stream()
                .map(this::convertToSummaryResponse)
                .collect(Collectors.toList());
    }

    public List<MemberSummaryResponse> searchMembersByName(String name) {

        return memberProfileRepository.findAllWithMember().stream()
                .filter(profile -> profile.getName().contains(name))
                .map(this::convertToSummaryResponse)
                .collect(Collectors.toList());
    }

    public List<MemberSummaryResponse> getMyPartMembers(Long currentMemberId) {

        Member me = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        String myPart = me.getRole().name();

        return memberProfileRepository.findByGenerationAndPart(null, myPart).stream()
                .map(this::convertToSummaryResponse)
                .collect(Collectors.toList());
    }

    public List<String> getMemberProjects(Long userId) {
        return projectMemberRepository.findAll().stream()
                .filter(pm -> pm.getMember().getId().equals(userId))
                .map(pm -> pm.getProject().getProjectName())
                .collect(Collectors.toList());
    }


    private MemberProfile findProfileByMemberIdOptimized(Long memberId) {
        return memberProfileRepository.findByMemberIdWithMember(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.PROFILE_NOT_FOUND));
    }


    private MemberDetailResponse convertToDetailResponse(MemberProfile profile) {
        Member member = profile.getMember();
        return MemberDetailResponse.builder()
                .id(member.getId())
                .name(profile.getName())
                .email(member.getEmail())
                .studentId(profile.getStudentId())
                .major(profile.getMajor())
                .generation(profile.getGeneration())
                .bio(profile.getBio())
                .profileImageUrl(profile.getProfileImageUrl())
                .role(member.getRole().name())
                .build();
    }


    private MemberSummaryResponse convertToSummaryResponse(MemberProfile profile) {
        Member member = profile.getMember();
        return MemberSummaryResponse.builder()
                .id(member.getId())
                .name(profile.getName())
                .profileImageUrl(profile.getProfileImageUrl())
                .role(member.getRole().name())
                .generation(profile.getGeneration())
                .build();
    }

    private String getDefaultProfileImageUrl() {
        if (s3Properties.getS3() != null 
                && s3Properties.getS3().getUrlPrefix() != null 
                && s3Properties.getS3().getPaths() != null 
                && s3Properties.getS3().getPaths().getDefaultFemaleProfileImage() != null) {
            return s3Properties.getS3().getUrlPrefix() 
                    + s3Properties.getS3().getPaths().getDefaultFemaleProfileImage();
        }
        // S3 설정이 없을 경우 빈 문자열 반환
        return "";
    }
}