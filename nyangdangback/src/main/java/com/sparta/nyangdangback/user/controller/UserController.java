package com.sparta.nyangdangback.user.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.nyangdangback.jwt.JwtUtil;
import com.sparta.nyangdangback.user.dto.LoginRequestDto;
import com.sparta.nyangdangback.user.dto.SignupRequestDto;
import com.sparta.nyangdangback.user.service.KakaoService;
import com.sparta.nyangdangback.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;

    @GetMapping("/signup")
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }

    // 로그인 한 유저가 메인페이지를 요청할 때 유저의 이름 반환
//    @GetMapping("/user-info")
//    @ResponseBody
//    public String getUserName(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return userDetails.getUsername();
//    }
//
//    @GetMapping("/login")
//    public ModelAndView loginPage() {
//        return new ModelAndView("login");
//    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequestDto signupRequestDto, BindingResult bindingResult) {
        //userService.signup(signupRequestDto);
        return userService.signup(signupRequestDto, bindingResult);
        //return "회원가입 성공";//"redirect:/api/user/login";
    }

    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse reponse) {
        userService.login(loginRequestDto, reponse);
        return "로그인 성공";
    }

    @GetMapping("/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        String createToken = kakaoService.kakaoLogin(code, response);

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/api/index";
    }
}
