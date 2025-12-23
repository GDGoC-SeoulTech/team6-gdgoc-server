package com.gdgoc.arcive.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Major {
    COMPUTER_SCIENCE("컴퓨터공학과");

    private final String description;
}

