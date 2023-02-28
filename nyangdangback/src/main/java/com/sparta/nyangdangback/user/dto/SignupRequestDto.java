package com.sparta.nyangdangback.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignupRequestDto {
//    @NotNull(message = "아이디는 5 ~ 10자 이내, 영어 소문자와 숫자 조합으로 생성해주세요.")
//    @Pattern(regexp = "^[a-z0-9]{5,10}")
    //private String username;
    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 4, max = 10, message = "닉네임은 4 이상 10자 이하로 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9]{5,10}")
    private String username;
    @NotNull(message = "비밀번호는 필수입니다.")
    @Size(min = 4, max = 10, message = "닉네임은 4 이상 10자 이하로 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9\\d`~!@#$%^&*()-_=+]{5,10}$")
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}

