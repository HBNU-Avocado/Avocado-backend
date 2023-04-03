package io.wisoft.capstonedesign.domain.member.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateMemberRequest {
    @NotBlank private String nickname;
    @NotBlank private String email;
    @NotBlank private String password;
    @NotBlank private String phonenumber;
}