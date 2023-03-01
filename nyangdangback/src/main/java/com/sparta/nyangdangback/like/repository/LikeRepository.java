package com.sparta.nyangdangback.like.repository;

import com.sparta.nyangdangback.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {
    Like findByBlog_IdAndUser_Id(Long blogno,Long userno);
}
