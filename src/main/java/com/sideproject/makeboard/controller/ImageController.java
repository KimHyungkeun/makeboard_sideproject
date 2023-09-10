package com.sideproject.makeboard.controller;

import com.sideproject.makeboard.dto.ImageRequestInfo;
import com.sideproject.makeboard.dto.ImageResponseInfo;
import com.sideproject.makeboard.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게시판 API 목록")
@Slf4j
@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;
    private ImageController(ImageService imageService) {
        this.imageService = imageService;
    }
    @PostMapping
    @Operation(summary = "이미지 파일을 첨부")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이미지 첨부 성공"),
            @ApiResponse(responseCode = "400", description = "requestBody의 imageUrl이 이미지파일이 아니거나 또는 20MB를 초과하는 이미지파일을 첨부할 경우"),
    })
    public ResponseEntity<?> setImageInfo(@RequestBody ImageRequestInfo imageRequestInfo) {
        return new ResponseEntity<ImageResponseInfo>(imageService.setImage(), HttpStatus.OK);
    }
}
