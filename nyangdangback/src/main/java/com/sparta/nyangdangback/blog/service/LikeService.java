package com.sparta.nyangdangback.blog.service;

import com.sparta.nyangdangback.blog.dto.BlogResponseDto;
import com.sparta.nyangdangback.blog.entity.Blog;
import com.sparta.nyangdangback.blog.repository.BlogRepository;
import com.sparta.nyangdangback.blog.entity.Like;
import com.sparta.nyangdangback.blog.repository.LikeRepository;
import com.sparta.nyangdangback.user.entity.User;
import com.sparta.nyangdangback.util.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.nyangdangback.util.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final BlogRepository blogRepository;
    private final LikeRepository likeRepository;
    private final BlogService blogService;

    @Transactional
    public ResponseEntity<BlogResponseDto> likeBlog(Long blogno, User user) {
        Blog blog = blogRepository.findById(blogno).orElseThrow(
                ()-> new CustomException(NOT_FOUND_DATA));

        Long likeCheck = likeRepository.countByBlog_IdAndUser_Id(blogno, user.getId());
        boolean heart = false;
        if(likeCheck == 0){ //좋아용
            heart = true;
            System.out.println("00000heart = " + likeCheck);
            blogService.likeBlog(blog,heart);
            Like like = likeRepository.saveAndFlush(new Like(blog,user));
        }else if(likeCheck ==1) { //좋아요 취소ㅠ
            System.out.println("11111heart = " + likeCheck);
            blogService.likeBlog(blog,heart);
            likeRepository.deleteByBlog_IdAndUser_Id(blogno, user.getId());
        }else {
            System.out.println("-----likeCheck = " + likeCheck);
            throw new CustomException(DUPLICATE_RESOURCE);
        }
//            throw new CustomException(DUPLICATE_RESOURCE);

        return ResponseEntity.ok().body(BlogResponseDto.of(blog));
    }
}
