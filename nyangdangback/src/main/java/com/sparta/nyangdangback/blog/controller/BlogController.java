package com.sparta.nyangdangback.blog.controller;

import com.sparta.nyangdangback.blog.dto.BlogRequestDto;
import com.sparta.nyangdangback.blog.dto.BlogResponseDto;
import com.sparta.nyangdangback.blog.service.BlogService;
import com.sparta.nyangdangback.config.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogService blogService;

    //게시글 작성
//    @PostMapping
//    public ResponseEntity<BlogResponseDto> createBlog( @RequestBody BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return blogService.createBlog(requestDto,userDetails.getUser());
//    }
//

//    @ResponseBody //title,contents 따로 받는 방법
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<BlogResponseDto> createBlog(@RequestPart(value = "image") MultipartFile image,BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails)throws IOException {
//        return blogService.createBlog(image,requestDto,userDetails.getUser());
//    }

    @ResponseBody //dto에 title, contents 한번에 받는 방법
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BlogResponseDto> createBlog(@RequestPart MultipartFile image, @RequestPart BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        System.out.println("-------image.getOriginalFilename() = " + image.getOriginalFilename());
        System.out.println("-------image.getContentType() = " + image.getContentType());
        System.out.println("-------requestDtoTitle = " + requestDto.getTitle());
        System.out.println("-------requestDtoContents = " + requestDto.getContents());
        return blogService.createBlog(image, requestDto, userDetails.getUser());
    }

//  @ResponseBody //이미지까지 dto에 넣어 한번에 받는 방법 - 실패
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<BlogResponseDto> createBlog(@RequestPart AllDto allDto, @AuthenticationPrincipal UserDetailsImpl userDetails)throws IOException {
//        BlogRequestDto blogRequestDto = null;
//        blogRequestDto.setTitle(allDto.getTitle());
//        blogRequestDto.setContents(allDto.getContents());
//      return blogService.createBlog(allDto.getImage(), blogRequestDto,userDetails.getUser());
//    }
//
//  @ResponseBody //이미지만 받기 테스트
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<BlogResponseDto> createBlog(@RequestPart(value = "image") MultipartFile image, @AuthenticationPrincipal UserDetailsImpl userDetails)throws IOException {
//      System.out.println("----------------------image = " + image.isEmpty());
//      System.out.println("----------------------image = " + image.getSize());
//      System.out.println("----------------------image = " + image.getOriginalFilename());
//
//      BlogRequestDto requestDto=new BlogRequestDto();
//        requestDto.setTitle("하드코딩");
//        requestDto.setContents("hardcoding");
//      return blogService.createBlog(image,requestDto,userDetails.getUser());
//    }

    //게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<BlogResponseDto>> getBlogs(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return blogService.getBlogs(userDetails.getUser());
    }

    //선택한 게시글 상세 조회
    @GetMapping("/{blogno}")
    public ResponseEntity<BlogResponseDto> getBlog(@PathVariable Long blogno,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return blogService.getBlog(blogno,userDetails.getUser());
    }

    //선택한 게시글 수정
    @PatchMapping("/{blogno}")
    public ResponseEntity<BlogResponseDto> updateBlog(@PathVariable Long blogno, @RequestBody BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return blogService.updateBlog(blogno, requestDto, userDetails.getUser());
    }

    //선택한 게시글 삭제
    @DeleteMapping("/{blogno}")
    public ResponseEntity<BlogResponseDto> deleteBlog(@PathVariable Long blogno, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return blogService.deleteBlog(blogno, userDetails.getUser());
    }

}
