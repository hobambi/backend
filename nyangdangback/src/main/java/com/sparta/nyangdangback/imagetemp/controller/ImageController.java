package com.sparta.nyangdangback.imagetemp.controller;

import com.sparta.nyangdangback.imagetemp.ImageDto;
import com.sparta.nyangdangback.imagetemp.ImageEntity;
import com.sparta.nyangdangback.imagetemp.S3Uploader;
import com.sparta.nyangdangback.imagetemp.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(value = "/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final S3Uploader s3Uploader;
    private Object fileSize;

//    @PostMapping
//    public ResponseEntity<ImageDto> uploadImage(@RequestPart("file") MultipartFile multipartFile) {
//        return ResponseEntity.ok(imageService.save(multipartFile));
//    }

    @ResponseBody
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long saveDiary(HttpServletRequest request, @RequestParam(value = "image") MultipartFile image, ImageEntity imageEntity) throws IOException {
        Long imageId = imageService.saveImage(image, imageEntity);
        return imageId;
    }


//    @PostMapping
//    public ResponseEntity<String> uploadFile(@RequestParam("images") MultipartFile multipartFile)throws IOException {
//        return ResponseEntity.ok().body(s3Uploader.upload(multipartFile));
//
//
//    }

    public int solution(String s) {
        int answer = 0;
        answer = Integer.parseInt(s);
        return answer;
    }

    /////////////
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        s3Uploader.upload(multipartFile);
        System.out.println("ImageController.uploadFile");

        return "성공";
//                ApiResponse.success(
//                HttpStatus.CREATED, s3Uploader.upload(multipartFile));//.getInputStream())//, multipartFile.getOriginalFilename(), fileSize)
////        );
    }



}
