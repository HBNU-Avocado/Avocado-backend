package io.wisoft.capstonedesign.domain.staff.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.wisoft.capstonedesign.domain.board.web.dto.BoardDto;
import io.wisoft.capstonedesign.domain.review.web.dto.ReviewDto;
import io.wisoft.capstonedesign.domain.staff.application.StaffMyPageService;
import io.wisoft.capstonedesign.domain.staff.web.dto.Result;
import io.wisoft.capstonedesign.global.annotation.swagger.SwaggerApi;
import io.wisoft.capstonedesign.global.annotation.swagger.SwaggerApiFailWithAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "의료진 마이페이지")
@RestController
@RequiredArgsConstructor
public class StaffMyPageController {

    private final StaffMyPageService staffMyPageService;

    @SwaggerApi(summary = "자신이 속한 병원의 리뷰 목록 조회", implementation = Result.class)
    @SwaggerApiFailWithAuth
    @GetMapping("/api/staff/{staff-id}/my-page/reviews")
    public Result reviewListByStaffId(@PathVariable("staff-id") final Long id) {
        return new Result(staffMyPageService.findReviewByStaffHospitalName(id)
                .stream().map(ReviewDto::new)
                .toList());
    }


    @SwaggerApi(summary = "자신이 댓글을 작성한 게시글 목록 조회", implementation = Result.class)
    @SwaggerApiFailWithAuth
    @GetMapping("/api/staff/{staff-id}/my-page/boards")
    public Result boardListByStaffId(@PathVariable("staff-id") final Long id) {
        return new Result(staffMyPageService.findBoardListByStaffId(id).stream()
                .map(BoardDto::new)
                .toList());
    }
}
