package com.gdgoc.arcive.domain.member.repository;

import com.gdgoc.arcive.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}

