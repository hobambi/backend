package com.sparta.nyangdangback.blog.dto;

import com.sparta.nyangdangback.blog.entity.Blog;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BlogResponseDto {
    private Long id;
    private String title;
    private String contents;
    private LocalDateTime createAt;

    @Builder
    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.contents = blog.getContent();
        this.createAt = blog.getCreateAt();
    }

    public static BlogResponseDto of(Blog blog) {
        return BlogResponseDto.builder()
                .blog(blog)
                .build();
    }


}

