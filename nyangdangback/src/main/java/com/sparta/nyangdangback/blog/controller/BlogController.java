package com.sparta.nyangdangback.blog.controller;

import com.sparta.nyangdangback.blog.dto.BlogRequestDto;
import com.sparta.nyangdangback.blog.dto.BlogResponseDto;
import com.sparta.nyangdangback.blog.service.BlogService;
import com.sparta.nyangdangback.config.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogService blogService;

    //게시글 작성
    @PostMapping
    public ResponseEntity<BlogResponseDto> createBlog( @RequestBody BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return blogService.createBlog(requestDto,userDetails.getUser());
    }

    //게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<BlogResponseDto>> getBlogs() {
        return blogService.getBlogs();
    }

    //선택한 게시글 상세 조회
    @GetMapping("/{blogno}")
    public ResponseEntity<BlogResponseDto> getBlog(@PathVariable Long blogno) {
        return blogService.getBlog(blogno);
    }

    //선택한 게시글 수정
    @PatchMapping("/{blogno}")
    public ResponseEntity<BlogResponseDto> updateBlog(@PathVariable Long blogno,@RequestBody BlogRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return blogService.updateBlog(blogno,requestDto,userDetails.getUser());
    }

    //선택한 게시글 삭제
    @DeleteMapping("/{blogno}")
    public ResponseEntity<BlogResponseDto> deleteBlog(@PathVariable Long blogno,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return blogService.deleteBlog(blogno,userDetails.getUser());
    }

}
