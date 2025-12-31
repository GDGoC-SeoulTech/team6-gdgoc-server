package com.gdgoc.arcive.domain.admin.service;

import com.gdgoc.arcive.domain.member.dto.MemberResponse;
import com.gdgoc.arcive.domain.member.dto.UpdateMemberRoleRequest;
import com.gdgoc.arcive.domain.member.entity.Member;
import com.gdgoc.arcive.domain.member.entity.Role;
import com.gdgoc.arcive.domain.member.exception.MemberErrorCode;
import com.gdgoc.arcive.domain.member.exception.MemberException;
import com.gdgoc.arcive.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final MemberRepository memberRepository;

    /**
     * 가입 사용자 목록 조회 (승인 대기 포함)
     */
    public List<MemberResponse> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberResponse::from)
                .toList();
    }

    /**
     * 멤버 승인 (PENDING -> MEMBER)
     */
    @Transactional
    public void approveMember(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        
        member.approve();
    }

    /**
     * 멤버 권한 수정
     */
    @Transactional
    public void updateMemberRole(Long userId, UpdateMemberRoleRequest request) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        
        member.updateRole(request.role());
    }
}

