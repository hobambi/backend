package com.sparta.nyangdangback.blog.service;

import com.sparta.nyangdangback.blog.dto.BlogRequestDto;
import com.sparta.nyangdangback.blog.dto.BlogResponseDto;
import com.sparta.nyangdangback.blog.entity.Blog;
import com.sparta.nyangdangback.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public ResponseEntity<BlogResponseDto> createBlog(BlogRequestDto requestDto) {
        System.out.println("BlogService.createBlog");
        Blog blog = blogRepository.saveAndFlush(new Blog(requestDto));
//        BlogResponseDto responseDto = new BlogResponseDto(blog);
        return ResponseEntity.ok().body(BlogResponseDto.of(blog));
    }
}
