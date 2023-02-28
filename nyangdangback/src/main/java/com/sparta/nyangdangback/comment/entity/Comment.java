package com.sparta.nyangdangback.comment.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long blogno;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "blogno")
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Builder
    public Comment(String comment, Blog blog, User user) {
        this.comment = comment;
        this.blog = blog;
        this.user = user;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }
}
