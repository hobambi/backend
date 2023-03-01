package com.sparta.nyangdangback.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class BlogRequestDto {

    private String imageUrl;
    private String title;
    private String contents;

}
