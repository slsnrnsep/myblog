package com.personalproject.myblog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.personalproject.myblog.dto.SignupRequestDto;
import com.personalproject.myblog.security.UserDetailsImpl;
import com.personalproject.myblog.service.UserService;
import com.personalproject.myblog.service.kakao.KakaoOAuth2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    // 에러 문구 표시
    @GetMapping("/user/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(SignupRequestDto requestDto, Model model) {
        if (userService.registerUser(requestDto).equals("")) {
            return "login";
        } else {
            model.addAttribute("errortext", userService.registerUser(requestDto));
            return "signup";
        }
    }

    // 카카오 콜백
    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(String code) {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        userService.kakaoLogin(code);
        return "redirect:/";
    }

    // 유저 로그인 체크
    @GetMapping("/api/userCheck")
    public String userCheck(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            return userDetails.getUser().getUsername();
        }
        return "";
    }
}