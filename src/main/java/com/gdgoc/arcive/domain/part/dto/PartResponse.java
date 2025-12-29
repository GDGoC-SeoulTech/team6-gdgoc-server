package com.gdgoc.arcive.domain.part.dto;

import com.gdgoc.arcive.domain.part.entity.Part;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "파트 정보")
public record PartResponse(
        @Schema(description = "파트 ID", example = "1")
        Long id,

        @Schema(description = "파트 이름", example = "FE")
        String partName,

        @Schema(description = "파트 설명", example = "프론트엔드 개발 파트입니다.")
        String description
) {
    public static PartResponse from(Part part) {
        return new PartResponse(
                part.getId(),
                part.getPartName(),
                part.getDescription()
        );
    }
}