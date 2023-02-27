package com.sparta.nyangdangback.imagetemp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageDto {
    private String originalImageName;
    private String storedImageName;
    private long fileSize;

    @Builder
    public ImageDto(String originalImageName, String storedImageName, long fileSize) {
        this.originalImageName = originalImageName;
        this.storedImageName = storedImageName;
        this.fileSize = fileSize;
    }
}
