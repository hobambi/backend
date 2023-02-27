package com.sparta.nyangdangback.blog.entity;

import com.sparta.nyangdangback.blog.dto.BlogRequestDto;
import com.sparta.nyangdangback.user.entity.User;
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

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name="user_no")
    private User user;


    public Blog(String imageUrl, BlogRequestDto blogRequestDto, User user) {
        this.title = blogRequestDto.getTitle();
        this.contents = blogRequestDto.getContents();
        this.user = user;
        this.imageUrl = imageUrl;
    }


    public void update(BlogRequestDto blogRequestDto) {
        this.contents = blogRequestDto.getContents();
    }
}
