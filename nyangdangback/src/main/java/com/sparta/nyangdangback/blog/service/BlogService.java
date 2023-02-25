package com.sparta.nyangdangback.blog.service;

import com.sparta.nyangdangback.blog.dto.BlogRequestDto;
import com.sparta.nyangdangback.blog.dto.BlogResponseDto;
import com.sparta.nyangdangback.blog.entity.Blog;
import com.sparta.nyangdangback.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    //게시글 작성
    @Transactional
    public ResponseEntity<BlogResponseDto> createBlog(BlogRequestDto blogRequestDto) {
        Blog blog = blogRepository.saveAndFlush(new Blog(blogRequestDto));
        return ResponseEntity.ok().body(BlogResponseDto.of(blog));
    }

    //게시글 전체 조회
    @Transactional(readOnly = true)
    public ResponseEntity<List<BlogResponseDto>> getBlogs() {
        List<Blog> blogList = blogRepository.findAllByOrderByModifiedAtDesc();
        List<BlogResponseDto> blogResponseDtoList = new ArrayList<>();
        for (Blog blog : blogList){
            blogResponseDtoList.add(new BlogResponseDto(blog));
        }
        return ResponseEntity.ok().body(blogResponseDtoList);
    }

    //선택한 게시글 조회
    @Transactional(readOnly = true)
    public ResponseEntity<BlogResponseDto> getBlog(Long blogno) {
        Blog blog = blogRepository.findById(blogno).orElseThrow(
                ()-> new IllegalArgumentException("없는 게시글 입니다."));
        return ResponseEntity.ok().body(BlogResponseDto.of(blog));
    }

    //선택한 게시글 수정
    @Transactional
    public ResponseEntity<BlogResponseDto> updateBlog(Long blogno,BlogRequestDto blogRequestDto) {
        Blog blog = blogRepository.findById(blogno).orElseThrow(
                ()-> new IllegalArgumentException("없는 게시글 입니다."));
        blog.update(blogRequestDto);
        return ResponseEntity.ok().body(BlogResponseDto.of(blog));
    }

    //선택한 게시글 삭제
    @Transactional
    public ResponseEntity deleteBlog(Long blogno) {
        Blog blog = blogRepository.findById(blogno).orElseThrow(
                () -> new IllegalArgumentException("없는 게시글 입니다."));
        blogRepository.deleteById(blogno);
        return ResponseEntity.ok().body("게시글 삭제 성공");
    }
}
