package com.sparta.nyangdangback.imagetemp.repository;

import com.sparta.nyangdangback.imagetemp.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity,Long> {
}
