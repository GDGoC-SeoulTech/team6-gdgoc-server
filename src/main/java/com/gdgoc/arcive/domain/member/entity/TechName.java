package com.gdgoc.arcive.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TechName {
    // BE
    JAVA("Java"),
    SPRING("Spring"),
    NODE_JS("Node.js"),
    PYTHON("Python"),
    DJANGO("Django"),
    FLASK("Flask"),

    // FE
    JAVASCRIPT("JavaScript"),
    TYPESCRIPT("TypeScript"),
    REACT("React"),
    VUE("Vue"),
    NEXT_JS("Next.js"),

    // APP
    KOTLIN("Kotlin"),
    SWIFT("Swift"),
    FLUTTER("Flutter"),
    REACT_NATIVE("React Native"),

    // DB
    MYSQL("MySQL"),
    POSTGRESQL("PostgreSQL"),
    MONGODB("MongoDB"),
    REDIS("Redis"),

    // ETC
    DOCKER("Docker"),
    KUBERNETES("Kubernetes"),
    AWS("AWS");

    private final String description;
}
