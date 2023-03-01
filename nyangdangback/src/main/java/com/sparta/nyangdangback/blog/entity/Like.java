package com.sparta.nyangdangback.blog.entity;

import com.sparta.nyangdangback.blog.entity.Blog;
import com.sparta.nyangdangback.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity(name = "blogLike")
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="blog_no")
    private Blog blog;

    @ManyToOne
    @JoinColumn(name="user_no")
    private User user;

    public Like(Blog blog, User user) {
        this.blog = blog;
        this.user = user;
    }
}
