package com.sparta.nyangdangback.blog.service;

import com.sparta.nyangdangback.blog.dto.BlogRequestDto;
import com.sparta.nyangdangback.blog.dto.BlogResponseDto;
import com.sparta.nyangdangback.blog.entity.Blog;
import com.sparta.nyangdangback.blog.entity.Like;
import com.sparta.nyangdangback.blog.repository.BlogRepository;
import com.sparta.nyangdangback.comment.dto.CommentResponseDto;
import com.sparta.nyangdangback.comment.entity.Comment;
import com.sparta.nyangdangback.comment.repository.CommentRepository;
import com.sparta.nyangdangback.imagetemp.S3Uploader;
import com.sparta.nyangdangback.blog.repository.LikeRepository;
import com.sparta.nyangdangback.util.CustomException;
import com.sparta.nyangdangback.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;


    //게시글 작성
    @Transactional
    public ResponseEntity<BlogResponseDto> createBlog(MultipartFile image, BlogRequestDto blogRequestDto, User user) throws IOException {
        System.out.println("-------blogRequestDto = " + blogRequestDto);
        System.out.println("blogRequestDto.getTitle = " + blogRequestDto.getTitle());
        System.out.println("blogRequestDto.contents = " + blogRequestDto.getContents());
        System.out.println("-------user = " + user.getUsername());

        String storedFileName = s3Uploader.upload(image, "images"); //s3에 업로드하기
        blogRequestDto.setImageUrl(storedFileName);

        Blog blog = blogRepository.saveAndFlush(new Blog(blogRequestDto, user));
        return ResponseEntity.ok().body(BlogResponseDto.of(blog));
    }

    //게시글 전체 조회
    @Transactional(readOnly = true)
    public ResponseEntity<List<BlogResponseDto>> getBlogs(User user) {
        List<Blog> blogList = blogRepository.findAllByOrderByModifiedAtDesc();
        List<BlogResponseDto> blogResponseDtoList = new ArrayList<>();

        for (Blog blog : blogList) {
            boolean heart = isHeart(user, blog);
            List<CommentResponseDto> commentRepositoryList = getComment(blog);
            blogResponseDtoList.add(new BlogResponseDto(blog, commentRepositoryList, heart));
        }
        return ResponseEntity.ok().body(blogResponseDtoList);
    }


    //선택한 게시글 조회
    @Transactional(readOnly = true)
    public ResponseEntity<BlogResponseDto> getBlog(Long blogno, User user) {
        Blog blog = blogRepository.findById(blogno).orElseThrow(
                () -> new CustomException(NOT_FOUND_DATA));
        List<CommentResponseDto> commentRepositoryList = getComment(blog);
        boolean heart = isHeart(user, blog);
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog, commentRepositoryList, heart);
        return ResponseEntity.ok().body(blogResponseDto);
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

    //게시글에 있는 댓글 가져오기
    private List<CommentResponseDto> getComment(Blog blog) {
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        List<Comment> commentList = commentRepository.findAllByBlogOrderByCreateAtDesc(blog);
        for (Comment c : commentList) {
            commentResponseDtoList.add(new CommentResponseDto(c));
        }
        return commentResponseDtoList;
    }

    //게시글 하트 가져오기
    private boolean isHeart(User user, Blog blog) {
        boolean heart = false;
        Long likeCheck = likeRepository.countByBlog_IdAndUser_Id(blog.getId(), user.getId());
        if (likeCheck != 0)
            heart = true;
        return heart;
    }

    //게시글 좋아요 누르기
    public void likeBlog(Blog blog, boolean heart) {
        Long likeNo = blog.getLikes();
        if (heart) blog.like(likeNo + 1);
        else blog.like(likeNo - 1);
    }
}
