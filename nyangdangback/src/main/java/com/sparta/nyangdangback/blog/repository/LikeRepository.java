package com.sparta.nyangdangback.blog.repository;

import com.sparta.nyangdangback.blog.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {
//    Like findAllByBlog_IdAndUser_Id(Long blogno,Long userno);

    Long countByBlog_IdAndUser_Id(Long blogno, Long userno);

    void deleteByBlog_IdAndUser_Id(Long blogno, Long userno);

}
