package com.sparta.nyangdangback.comment.controller;

import com.amazonaws.Response;
import com.sparta.nyangdangback.comment.dto.CommentRequestDto;
import com.sparta.nyangdangback.comment.dto.CommentResponseDto;
import com.sparta.nyangdangback.comment.dto.StatusMsgResponseDto;
import com.sparta.nyangdangback.comment.service.CommentService;
import com.sparta.nyangdangback.config.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController // Json형태로 Body에 가져옴
@RequiredArgsConstructor // final로 선언된 멤버변수 자동생성(DI 의존성 주입)
@RequestMapping("/api/blogs/{id}")
public class CommentController {
    // Service단 연결
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(id, commentRequestDto, userDetails.getUser());
    }

//    // 댓글 수정
//    @PutMapping("/api/blogs/{id}/comment")
//    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return commentService.updateComment(id, commentRequestDto.getComment(), userDetails.getUser());
//    }
//
//    // 댓글 삭제
//    @DeleteMapping("/api/blogs/{id}/comment")
//    public ResponseEntity<StatusMsgResponseDto> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return commentService.deleteComment(id, request);
//    }
}
