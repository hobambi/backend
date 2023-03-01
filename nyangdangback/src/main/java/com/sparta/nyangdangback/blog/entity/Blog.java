package com.sparta.nyangdangback.blog.entity;

import com.sparta.nyangdangback.blog.dto.BlogRequestDto;
import com.sparta.nyangdangback.like.entity.Like;
import com.sparta.nyangdangback.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private Long likes;

    @ManyToOne
    @JoinColumn(name="user_no")
    private User user;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "blog")
    private List<Like> like = new ArrayList<>();


    public Blog(BlogRequestDto blogRequestDto, User user) {
        this.title = blogRequestDto.getTitle();
        this.contents = blogRequestDto.getContents();
        this.user = user;
        this.imageUrl = blogRequestDto.getImageUrl();
        this.likes = 0L;
    }

    public void like(Long likes){
        this.likes = likes;
    }

    public void update(BlogRequestDto blogRequestDto) {
        this.contents = blogRequestDto.getContents();
    }
}
