package io.wisoft.capstonedesign.domain.board.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateBoardRequest(
        @NotNull Long memberId,
        @NotBlank String title,
        @NotBlank String body,
        @NotBlank String dept,
        @NotBlank String boardPhotoPath
) { }