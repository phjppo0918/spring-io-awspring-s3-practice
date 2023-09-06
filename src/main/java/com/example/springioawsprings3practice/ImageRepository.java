package com.example.springioawsprings3practice;

import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class ImageRepository {
    S3Template s3template;
    String bucketName;

    public ImageRepository(S3Template s3template,
                           @Value("${spring.cloud.aws.s3.bucket}") String bucketName) {
        this.s3template = s3template;
        this.bucketName = bucketName;
    }


    public String save(MultipartFile file) {
        final S3Resource result = s3template.upload(bucketName, generateName(), getInputStream(file));
        return getUrl(result);
    }

    private InputStream getInputStream(MultipartFile file) {
        try {
            return file.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private String generateName() {
        return UUID.randomUUID().toString();
    }

    private String getUrl(S3Resource s3Resource) {
        try {
            return s3Resource.getURL().toString();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
