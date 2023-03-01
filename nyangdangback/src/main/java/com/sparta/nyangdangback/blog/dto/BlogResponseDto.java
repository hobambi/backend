package com.sparta.nyangdangback.blog.dto;

import com.sparta.nyangdangback.blog.entity.Blog;
import com.sparta.nyangdangback.comment.dto.CommentResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class BlogResponseDto {
    private Long id;
    private String username;
    private String title;
    private String contents;
    private String imageUrl;
    private LocalDateTime createAt;
    private Long likes;

    private boolean heart;

    private List<CommentResponseDto> comments;

    @Builder
    public BlogResponseDto(Blog blog,List<CommentResponseDto> comments,boolean heart) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.contents = blog.getContents();
        this.createAt = blog.getCreateAt();
        this.username = blog.getUser().getUsername();
        this.imageUrl=blog.getImageUrl();
        this.comments = comments;
        this.likes = blog.getLikes();
        this.heart = heart;
    }

    public static BlogResponseDto of(Blog blog) {
        return BlogResponseDto.builder()
                .blog(blog)
                .build();
    }

}

