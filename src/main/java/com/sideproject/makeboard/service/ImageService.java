package com.sideproject.makeboard.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.dir}")
    private String dir;

    private final AmazonS3Client amazonS3Client;
    private ImageService (AmazonS3Client amazonS3Client) { this.amazonS3Client = amazonS3Client;}

    public String setImage (MultipartFile multipartFile) throws IOException {
        String s3FileName = dir + "/" + UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
        long size = multipartFile.getSize();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentType(multipartFile.getContentType());
        objMeta.setContentLength(size);

        amazonS3Client.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

        return amazonS3Client.getUrl(bucket, s3FileName).toString();
    }
}
