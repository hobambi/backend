package com.sparta.nyangdangback.imagetemp.service;

import com.sparta.nyangdangback.imagetemp.ImageEntity;
import com.sparta.nyangdangback.imagetemp.S3Uploader;
import com.sparta.nyangdangback.imagetemp.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;


    private final S3Uploader s3Uploader;

    @Transactional
    public Long saveImage(MultipartFile image, ImageEntity imageEntity) throws IOException {

        if (!image.isEmpty()) {
            String storedFileName = s3Uploader.upload(image, "images");
            imageEntity.setImageUrl(storedFileName);
        }
        ImageEntity savedImage = imageRepository.save(imageEntity);
        return savedImage.getId();
    }
}
