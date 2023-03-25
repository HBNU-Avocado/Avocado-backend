package io.wisoft.capstonedesign.domain.review.web.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateReviewRequest {
    private String newTitle;
    private String newBody;
}
