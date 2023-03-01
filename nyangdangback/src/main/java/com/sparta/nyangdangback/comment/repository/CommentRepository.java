package com.sparta.nyangdangback.comment.repository;

import com.sparta.nyangdangback.blog.entity.Blog;
import com.sparta.nyangdangback.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBlogOrderByCreateAtDesc(Blog blog);
    void deleteAllByBlog(Blog blog);
}