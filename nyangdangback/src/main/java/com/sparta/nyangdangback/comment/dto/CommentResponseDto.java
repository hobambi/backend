package com.sparta.nyangdangback.comment.dto;

import com.sparta.nyangdangback.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private Long blogno;
    private String comment;
    private String username;

    @Builder
    public CommentRequestDto(Comment comment) {
        this.blogno = comment.blogno();
        this.comment = comment.comment();
        this.username = comment.getUser().getUsername();
    }
}
