package com.sideproject.makeboard.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;
    private ImageService (AmazonS3Client amazonS3Client) { this.amazonS3Client = amazonS3Client;}

    public String setImage (MultipartFile multipartFile) throws IOException {
        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        String s3FileName = simpleDateFormat.format(nowDate) + "/" + UUID.randomUUID() + "/" + multipartFile.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentType(multipartFile.getContentType());
        objMeta.setContentDisposition("attachment");

        amazonS3Client.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

        return s3FileName;
    }
}
