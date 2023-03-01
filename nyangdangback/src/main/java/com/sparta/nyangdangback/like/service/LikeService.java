package com.sparta.nyangdangback.like.service;

import com.sparta.nyangdangback.blog.dto.BlogResponseDto;
import com.sparta.nyangdangback.blog.entity.Blog;
import com.sparta.nyangdangback.blog.repository.BlogRepository;
import com.sparta.nyangdangback.blog.service.BlogService;
import com.sparta.nyangdangback.like.entity.Like;
import com.sparta.nyangdangback.like.repository.LikeRepository;
import com.sparta.nyangdangback.user.entity.User;
import com.sparta.nyangdangback.util.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.nyangdangback.util.ErrorCode.NOT_FOUND_DATA;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final BlogRepository blogRepository;
    private final LikeRepository likeRepository;
    private final BlogService blogService;

    @Transactional
    public ResponseEntity<BlogResponseDto> likeBlog(Long blogno, User user) {
        System.out.println("111111111111111111111111");
        Blog blog = blogRepository.findById(blogno).orElseThrow(
                ()-> new CustomException(NOT_FOUND_DATA));
        System.out.println("222222222222222222222222222");
        blogService.likeBlog(blog);
        System.out.println("333333333333333333333333333333");
        Like like = likeRepository.saveAndFlush(new Like(blog,user));
        System.out.println("44444444444444444444444444");
        return ResponseEntity.ok().body(BlogResponseDto.of(blog));
    }
}
