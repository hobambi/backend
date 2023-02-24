package com.sparta.nyangdangback.blog.controller;

import com.sparta.nyangdangback.blog.dto.BlogRequestDto;
import com.sparta.nyangdangback.blog.dto.BlogResponseDto;
import com.sparta.nyangdangback.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogService blogService;

    //게시글 작성
    @PostMapping
    public ResponseEntity<BlogResponseDto> createBlog(@RequestBody BlogRequestDto requestDto){
        System.out.println("BlogController.createBlog");
        return blogService.createBlog(requestDto);
    }
}
