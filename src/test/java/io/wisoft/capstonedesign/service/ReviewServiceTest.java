package io.wisoft.capstonedesign.service;


import io.wisoft.capstonedesign.domain.Member;
import io.wisoft.capstonedesign.domain.Review;
import io.wisoft.capstonedesign.domain.enumeration.ReviewStatus;
import io.wisoft.capstonedesign.exception.IllegalValueException;
import io.wisoft.capstonedesign.repository.ReviewRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReviewServiceTest {

    @Autowired ReviewService reviewService;
    @Autowired ReviewRepository reviewRepository;

    @Test
    public void 리뷰작성() throws Exception {
        //given -- 조건

        //리뷰를 작성할 회원 생성
        Member member = Member.newInstance("lee", "ldy_1204@naver.com", "1111", "0000");
        //리뷰 생성
        Review review = Review.createReview(member, "친절해요", "자세히 진료해줘요", "사진_링크", 5, "아보카도 병원");

        //when -- 동작
        Long saveId = reviewService.save(review);

        //then -- 검증
        Review getReview = reviewRepository.findOne(saveId); //저장된 리뷰

        Assertions.assertThat(getReview).isEqualTo(review);
        Assertions.assertThat(getReview.getId()).isEqualTo(review.getId());
        Assertions.assertThat(getReview.getStatus()).isEqualTo(ReviewStatus.WRITE);
    }

    //리뷰삭제
    @Test
    public void 리뷰삭제() throws Exception {
        //given -- 조건

        //리뷰를 작성할 회원 생성
        Member member = Member.newInstance("lee", "ldy_1204@naver.com", "1111", "0000");
        //리뷰 생성
        Review review = Review.createReview(member, "친절해요", "자세히 진료해줘요", "사진_링크", 5, "아보카도 병원");
        //리뷰 저장
        Long saveId = reviewService.save(review);

        //when -- 동작
        reviewService.deleteReview(saveId);

        //then -- 검증
        Review getReview = reviewService.findOne(saveId);

        Assertions.assertThat(getReview.getStatus()).isEqualTo(ReviewStatus.DELETE);
    }

    //리뷰 중복 삭제요청
    @Test(expected = IllegalStateException.class)
    public void 리뷰_삭제_중복요청() throws Exception {
        //given -- 조건

        //리뷰를 작성할 회원 생성
        Member member = Member.newInstance("lee", "ldy_1204@naver.com", "1111", "0000");
        //리뷰 생성
        Review review = Review.createReview(member, "친절해요", "자세히 진료해줘요", "사진_링크", 5, "아보카도 병원");
        //리뷰 저장
        Long saveId = reviewService.save(review);

        //when -- 동작
        reviewService.deleteReview(saveId);
        reviewService.deleteReview(saveId);

        //then -- 검증
        fail("중복 삭제 요청으로 인한 예외가 발생해야 한다.");
    }

    //리뷰 별점 1~5만족?
    @Test(expected = IllegalValueException.class)
    public void 리뷰_별점_범위초과() throws Exception {
        //given -- 조건
        //리뷰를 작성할 회원 생성
        Member member = Member.newInstance("lee", "ldy_1204@naver.com", "1111", "0000");

        int starPoint = 6;
        //리뷰 생성
        Review review = Review.createReview(member, "친절해요",
                "자세히 진료해줘요", "사진_링크",
                starPoint, "아보카도 병원");

        //when -- 동작
        Long saveId = reviewService.save(review);

        //then -- 검증
        fail("리뷰의 별점이 1~5 사이의 범위가 아니므로 예외가 발생해야 한다.");
    }

}