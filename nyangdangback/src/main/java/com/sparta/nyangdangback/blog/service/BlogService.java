package com.sparta.nyangdangback.blog.service;

import com.sparta.nyangdangback.blog.dto.BlogRequestDto;
import com.sparta.nyangdangback.blog.dto.BlogResponseDto;
import com.sparta.nyangdangback.blog.entity.Blog;
import com.sparta.nyangdangback.blog.repository.BlogRepository;
import com.sparta.nyangdangback.dto.MessageDto;
import com.sparta.nyangdangback.imagetemp.S3Uploader;
import com.sparta.nyangdangback.util.CustomException;
import com.sparta.nyangdangback.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sparta.nyangdangback.util.ErrorCode.*;


@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final S3Uploader s3Uploader;


    //게시글 작성
    @Transactional
    public ResponseEntity<BlogResponseDto> createBlog(MultipartFile image,BlogRequestDto blogRequestDto, User user) throws IOException {
        String storedFileName = s3Uploader.upload(image,"images"); //s3에 업로드하기
        Blog blog = blogRepository.saveAndFlush(new Blog(storedFileName,blogRequestDto, user));
        return ResponseEntity.ok().body(BlogResponseDto.of(blog));
    }

    //게시글 전체 조회
    @Transactional(readOnly = true)
    public ResponseEntity<List<BlogResponseDto>> getBlogs() {
        List<Blog> blogList = blogRepository.findAllByOrderByModifiedAtDesc();
        List<BlogResponseDto> blogResponseDtoList = new ArrayList<>();
        for (Blog blog : blogList) {
            blogResponseDtoList.add(new BlogResponseDto(blog));
        }
        return ResponseEntity.ok().body(blogResponseDtoList);
    }

    //선택한 게시글 조회
    @Transactional(readOnly = true)
    public ResponseEntity<BlogResponseDto> getBlog(Long blogno) {
        Blog blog = blogRepository.findById(blogno).orElseThrow(
                ()-> new CustomException(NOT_FOUND_DATA));
//        Blog blog = blogRepository.findById(blogno).orElseThrow(
//                () -> new IllegalArgumentException("없는 게시글 입니다."));
        return ResponseEntity.ok().body(BlogResponseDto.of(blog));
    }

    //선택한 게시글 수정
    @Transactional
    public ResponseEntity<BlogResponseDto> updateBlog(Long blogno, BlogRequestDto blogRequestDto, User user) {
        Blog blog = blogRepository.findById(blogno).orElseThrow(
                () -> new CustomException(NOT_FOUND_DATA));
//        if(user.getRole() == USER) { //관리자 없다고 가정.
        if (user.getUsername().equals(blog.getUser().getUsername()))
            blog.update(blogRequestDto);
        else throw new CustomException(FORBIDDEN_DATA);

//        }else {
//            blog.update(blogRequestDto);
//        }
        return ResponseEntity.ok().body(BlogResponseDto.of(blog));
    }

    //선택한 게시글 삭제
    @Transactional
    public ResponseEntity deleteBlog(Long blogno, User user) {
        Blog blog = blogRepository.findById(blogno).orElseThrow(
                () -> new CustomException(NOT_FOUND_DATA));

//        if(user.getRole()==USER) { //관리자 없다고 가정.
        if (user.getUsername().equals(blog.getUser().getUsername()))
            blogRepository.deleteById(blogno);
//        else return ResponseEntity.badRequest().body(
        else throw new CustomException(FORBIDDEN_DATA);
//        }else {
//            blogRepository.deleteById(blogno);
//        }

        return ResponseEntity.ok().body("게시글 삭제 성공");
    }
}
