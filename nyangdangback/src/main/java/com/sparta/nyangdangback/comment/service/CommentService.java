package com.sparta.nyangdangback.comment.service;

import com.sparta.nyangdangback.comment.dto.CommentRequestDto;
import com.sparta.nyangdangback.comment.dto.CommentResponseDto;
import com.sparta.nyangdangback.comment.entity.Comment;
import com.sparta.nyangdangback.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final JwtUtil jwtUtil;

    // 댓글 작성
    public ResponseEntity<CommentResponseDto> createComment(Long blogno, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        User findUser = isValidTokenAndUser(request);

        Optional<Blog> findBlog = blogRepository.findById(id);
        if(findBlog.isEmpty()) {
            throw new CustomException(ErrorCodeEnum.NOT_EXIST_RESOURCE);
        }
        // 댓글 생성
        Comment comment = Comment.builder()
                .user(findUser)
                .blog(findBlog.get())
                .comment(commentRequestDto.getComment())
                .build();
        // 댓글 DB 저장
        commentRepository.save(comment);
        return ResponseEntity.ok(CommentResponseDto.builder()
                .comment(comment)
                .build());
    }
}
