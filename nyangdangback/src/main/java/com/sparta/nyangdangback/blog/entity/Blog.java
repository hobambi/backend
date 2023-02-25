package com.sparta.nyangdangback.blog.entity;

import com.sparta.nyangdangback.blog.dto.BlogRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Blog extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    ///////////////////////////
//    private String originalImageName;
//    private String storedImageName;
//    private long fileSize;



    //////////////////////////

    public Blog(BlogRequestDto blogRequestDto) {
        this.title = blogRequestDto.getTitle();
        this.contents = blogRequestDto.getContents();
    }


    public void update(BlogRequestDto blogRequestDto) {
        this.contents = blogRequestDto.getContents();
    }
}
