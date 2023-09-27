package com.sideproject.makeboard.controller;

import com.sideproject.makeboard.dto.ImageResponseInfo;
import com.sideproject.makeboard.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Tag(name = "게시판 API 목록")
@Slf4j
@RestController
@RequestMapping("/image")

public class ImageController {

    private final ImageService imageService;

    private ImageController (ImageService imageService) {this.imageService = imageService;}

    @PostMapping
    @Operation(summary = "이미지 파일을 첨부")
    public ResponseEntity<?> setImageInfo(MultipartFile file) throws IOException {
        String[] imageFileFormat = {"jpg", "png", "jpeg", "gif", "bmp", "webp"};
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        // 파일크기가 20MB를 초과하면 BAD_REQUEST 발생
        if (file.getSize() > 20 * Math.pow(1024, 3)) {
            return new ResponseEntity<String>("File size over 20MB", HttpStatus.BAD_REQUEST);
        }

        // 업로드한 파일이 정의한 5가지 이미지 파일형식이 아니면 BAD_REQEUST 발생
        if (extension == null || !Arrays.asList(imageFileFormat).contains(extension.toLowerCase())) {
            return new ResponseEntity<String>("Must Upload Image file (Allows : jpg, png, jpeg, gif, bmp, webp)", HttpStatus.BAD_REQUEST);
        }

        String imagePath = imageService.setImage(file);
        ImageResponseInfo imageResponseInfo = new ImageResponseInfo();
        imageResponseInfo.setImageUrl(imagePath);

        return new ResponseEntity<ImageResponseInfo>(imageResponseInfo, HttpStatus.OK);
    }
}
