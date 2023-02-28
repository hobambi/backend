package com.sparta.nyangdangback.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController // Json형태로 Body에 가져옴
@RequiredArgsConstructor // final로 선언된 멤버변수 자동생성(DI 의존성 주입)
@RequestMapping("/api/blogs/{blogno}")
public class CommentController {
    // Service단 연결
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/api/blogs/{blogno}/comment")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long blogno, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest req) {
        return commentService.createComment(blogno, commentRequestDto, req);
    }
}
