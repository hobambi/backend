package com.sparta.nyangdangback.blog.controller;

import com.sparta.nyangdangback.blog.dto.BlogResponseDto;
import com.sparta.nyangdangback.config.UserDetailsImpl;
import com.sparta.nyangdangback.blog.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blogs")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{blogno}/like")
    public ResponseEntity<BlogResponseDto> likeBlog(@PathVariable Long blogno, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("LikeController.likeBlog");
        return likeService.likeBlog(blogno, userDetails.getUser());
    }
}
