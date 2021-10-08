package com.personalproject.myblog.dto;

import lombok.Getter;

@Getter
public class ReplyRequestDto {
    private Long postid;
    private String username;
    private String reply;
    private Long userId;
}
