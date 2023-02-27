package com.sparta.nyangdangback.blog.entity;

import com.sparta.nyangdangback.imagetemp.ImageDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ImageHandler { //로컬에 이미지 저장하는 방법 일단 안 쓰임
    public ImageDto parseImageInfo(
//            Long blogno,
            List<MultipartFile> multipartFiles
            
    ) throws Exception {
        ImageDto imageDto = null;
        //반환할 파일 리스트
//        List<Blog> imageList = new ArrayList<>();

        if (multipartFiles.isEmpty()) {
            return new ImageDto();
        }

        //파일 이름을 업로드한 날짜로 바꿔서 저장
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        //프로젝트 폴더에 저장하기 위해 절대 경로 설정(window의 Tomcat은 temp파일 이용)
        String absolutePath = new File("").getAbsolutePath()+"\\";

        //경로 지정하고 거기에 저장
        String path = "images/" + current_date;
        File file = new File(path);

        //경로가 존재하지 않을 경우
        if(!file.exists()){
            //경로 직접 생성
            file.mkdirs();
        }

        for(MultipartFile multipartFile : multipartFiles){
            //파일이 비어 있지 않은 경우에만 진행해야함
            if (!multipartFile.isEmpty()) {

                //jpeg,png,gif 만 받을거임
                String contentType = multipartFile.getContentType();
                String originalImageExtension;

                //확장자명이 없으면 잘 못된 파일이니까 바로 break
                if(!contentType.isEmpty()){
                    break;
                }else {
                    if (contentType.contains("image/jpeg")) {
                        originalImageExtension = ".jpg";
                    } else if (contentType.contains("image/png")) {
                        originalImageExtension = ".png";
                    } else if (contentType.contains("image/gif")) {
                        originalImageExtension = ".gif";
                    }else break;
                }

                //이름이 겹치면 안되니까 나노초까지 동원하여 지정
                String newFileName = Long.toString(System.nanoTime())+originalImageExtension;

                imageDto = ImageDto.builder()
                        .originalImageName(originalImageExtension)
                        .storedImageName(multipartFile.getOriginalFilename())
                        .fileSize(multipartFile.getSize())
                        .build();

                //저장된 파일로 변경하여 보여주기 위함
                file = new File(absolutePath + path + "/" + newFileName);
                multipartFile.transferTo(file);
            }
        }
        return imageDto;
    }
}
