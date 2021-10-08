package com.personalproject.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KakaoUserInfoDto {
    Long id;
    String email;
    String nickname;
}