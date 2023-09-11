package com.sideproject.makeboard.controller;

import com.sideproject.makeboard.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "게시판 API 목록")
@Slf4j
@RestController
@RequestMapping("/image")

public class ImageController {

    private final ImageService imageService;

    private ImageController (ImageService imageService) {this.imageService = imageService;}

    @PostMapping
    @Operation(summary = "이미지 파일을 첨부")
    public ResponseEntity<?> setImageInfo(MultipartFile[] multipartFileList) throws IOException {
        List<String> imagePathList = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFileList) {
            String imagePath = imageService.setImage(multipartFile);
            imagePathList.add(imagePath);
        }

        return new ResponseEntity<Object>(imagePathList, HttpStatus.OK);
    }
}
