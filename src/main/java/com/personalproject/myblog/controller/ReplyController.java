package com.personalproject.myblog.controller;

import com.personalproject.myblog.dto.ReplyRequestDto;
import com.personalproject.myblog.models.Reply;
import com.personalproject.myblog.repository.ReplyRepository;
import com.personalproject.myblog.security.UserDetailsImpl;
import com.personalproject.myblog.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReplyController {

    private final com.personalproject.myblog.repository.ReplyRepository ReplyRepository;
    private final com.personalproject.myblog.service.ReplyService ReplyService;

    // 로그인한 회원이 등록한 상품들 조회
    @GetMapping("/api/reply")
    public List<Reply> getReply(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        return ReplyService.getReply(userId);
    }

    // 게시글 id 로 댓글 조회
    @GetMapping("/api/reply/{postId}")
    public List<Reply> getReply(@PathVariable Long postId) {
        return ReplyService.getReply(postId);
    }

    // 댓글 작성
    @PostMapping("/api/reply")
    public boolean createReply(@RequestBody ReplyRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 로그인 되어 있는 ID
        if (userDetails != null) {
            Long userId = userDetails.getUser().getId();
            ReplyService.createReply(requestDto, userId);
            return true;
        }
        return false;
    }

    // 댓글 수정
    @PutMapping("/api/reply/{id}")
    public Long updateReply(@PathVariable Long id, @RequestBody ReplyRequestDto requestDto) {
        ReplyService.update(id, requestDto);
        return id;
    }

    // 댓글 삭제
    @DeleteMapping("/api/reply/{id}")
    public Long deleteReply(@PathVariable Long id) {
        ReplyRepository.deleteById(id);
        return id;
    }
}
