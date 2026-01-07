package com.gdgoc.arcive.domain.member.controller;

import com.gdgoc.arcive.domain.member.dto.MemberOnboardingRequest;
import com.gdgoc.arcive.domain.member.dto.MemberResponse;
import com.gdgoc.arcive.domain.member.dto.MemberUpdateRequest;
import com.gdgoc.arcive.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "멤버", description = "멤버 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/me/onboarding")
    public ResponseEntity<Void> onboardMember(@RequestBody MemberOnboardingRequest request) {
        Long currentMemberId = 1L;
        memberService.onboardMember(currentMemberId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<MemberResponse> getMemberProfile(@PathVariable Long userId) {
        MemberResponse response = memberService.getMemberProfile(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getMyProfile() {
        Long currentMemberId = 1L;
        MemberResponse response = memberService.getMemberProfile(currentMemberId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/me")
    public ResponseEntity<Void> updateMemberProfile(@RequestBody MemberUpdateRequest request) {
        Long currentMemberId = 1L;
        memberService.updateMemberProfile(currentMemberId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<List<MemberResponse>> getMemberList(
            @RequestParam(required = false) Integer generation,
            @RequestParam(required = false) String part) {
        return ResponseEntity.ok(memberService.getMemberList(generation, part));
    }

    @GetMapping("/search")
    public ResponseEntity<List<MemberResponse>> searchMembers(@RequestParam String name) {
        return ResponseEntity.ok(memberService.searchMembersByName(name));
    }

    @GetMapping("/{userId}/projects")
    public ResponseEntity<List<String>> getMemberProjects(@PathVariable Long userId) {
        return ResponseEntity.ok(memberService.getMemberProjects(userId));
    }

    @GetMapping("/me/part-members")
    public ResponseEntity<List<MemberResponse>> getMyPartMembers() {
        Long currentMemberId = 1L;
        return ResponseEntity.ok(memberService.getMyPartMembers(currentMemberId));
    }
}