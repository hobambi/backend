package com.sparta.nyangdangback.comment.service;

import com.sparta.nyangdangback.blog.entity.Blog;
import com.sparta.nyangdangback.blog.repository.BlogRepository;
import com.sparta.nyangdangback.comment.dto.CommentRequestDto;
import com.sparta.nyangdangback.comment.dto.CommentResponseDto;
import com.sparta.nyangdangback.comment.dto.StatusMsgResponseDto;
import com.sparta.nyangdangback.comment.entity.Comment;
import com.sparta.nyangdangback.comment.entity.enumSet.ErrorCodeEnum;
import com.sparta.nyangdangback.comment.repository.CommentRepository;
import com.sparta.nyangdangback.jwt.JwtUtil;
import com.sparta.nyangdangback.user.entity.User;
import com.sparta.nyangdangback.user.entity.UserRoleEnum;
import com.sparta.nyangdangback.user.repository.UserRepository;
import com.sparta.nyangdangback.util.CustomException;
import com.sparta.nyangdangback.util.ErrorCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final JwtUtil jwtUtil;

    // 댓글 작성
    public ResponseEntity<CommentResponseDto> createComment(Long id, CommentRequestDto commentRequestDto, User user) {
//        User findUser = isValidTokenAndUser(request);

        Optional<Blog> findBlog = blogRepository.findById(id);
        if(findBlog.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_DATA);
        }
        // 댓글 생성
        Comment comment = Comment.builder()
                .user(user)
                .blog(findBlog.get())
                .comment(commentRequestDto.getComment())
                .build();
        // 댓글 DB 저장
        commentRepository.save(comment);
        return ResponseEntity.ok(CommentResponseDto.builder()
                .comment(comment)
                .build());
    }

//    // 댓글 수정
//    public ResponseEntity<CommentResponseDto> updateComment(Long commentid, String commentCotent, HttpServletRequest request) {
//        User findUser = isValidTokenAndUser(request);
//
//        Optional<Comment> findComment = commentRepository.findById(commentid);
//        if(findComment.isEmpty()) {
//            throw new CustomException(ErrorCodeEnum.NOT_EXIST_RESOURCE);
//        }
//        // 권한 user, 자신이 쓴 댓글이 아닌경우
//        if (findUser.getRole()) == UserRoleEnum.USER && findComment.get().getUser() != findUser) {
//            throw new CustomException(ErrorCodeEnum.INVALID_AUTH_MEMBER);
//        }
//        // 자동감지로 댓글 수정
//        findComment.get().updateComment(commentCotent);
//        return ResponseEntity.ok().body(
//                CommentResponseDto.builder()
//                        .comment(findComment.get())
//                        .build()
//        );
//    }
//
//    // 댓글 삭제
//    public ResponseEntity<StatusMsgResponseDto> deleteComment(Long id, HttpServletRequest request) {
//        User findUser = isValidTokenAndUser(request);
//        // 댓글 번호로 해당 댓글 DB에서 가져오기
//        Optional<Comment> findComment = commentRepository.findById(id);
//        if(findComment.isEmpty()) {
//            throw new CustomException(ErrorCodeEnum.NOT_EXIST_RESOURCE);
//        }
//        // 권한 USER, 자신이 쓴 댓글이 아닌경우
//        if (findUser.getRole()) ==UserRoleEnum.USER && findComment.get().getUser() != findUser) {
//            throw new CustomException(ErrorCodeEnum.INVALID_AUTH_MEMBER);
//        }
//        // 해당 댓글 삭제
//        commentRepository.delete(findComment.get());
//        return ResponseEntity.ok(new StatusMsgResponseDto("댓글 삭제 완료!", HttpStatus.OK.value()));
//    }

//    // 토큰 유효성 확인 및 회원체크
//    private User isValidTokenAndUser(HttpServletRequest request) {
//        // 요청 Header로 전달되는 토큰 get
//        String token = jwtUtil.resolveToken(request);
//        Claims claims;
//        // token 있는지 없는지 확인
//        // Null이면 token 없음
//        if (token == null) {
//            throw new CustomException(ErrorCodeEnum.FOUND_NOT_TOKEN);
//        }
//        if (jwtUtil.validateToken(token)) {
//            claims = jwtUtil.getUserInfoFromToken(token); // 토큰에 있는 회원정보 가져오기
//        } else {
//            throw new CustomException(ErrorCodeEnum.INVALID_TOKEN);
//        }
//        //토큰에 생성시 사용한 회원명 얻어서 해당 회원이 있는지 DB에서 찾기
//        Optional<User> findUser = userRepository.findByUsername(claims.getSubject());
//        if (findUser.isEmpty()) {
//            throw new CustomException(ErrorCodeEnum.MEMBER_NOT_FOUND);
//        }
//        return findUser.get();
//    }
}
