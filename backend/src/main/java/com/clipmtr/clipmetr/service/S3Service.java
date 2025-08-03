package com.clipmtr.clipmetr.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.clipmtr.clipmetr.dto.BucketsDTO;
import com.clipmtr.clipmetr.dto.S3ObjectsDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class S3Service {

    String cliprBucketName = "acg-bucket-444-for-clipmetr";

    private final AmazonS3 amazonS3;
    public S3Service() {
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .build();

    }


    public Bucket createBucket(String bucketName) {
        Bucket bucket;
        if (amazonS3.doesBucketExistV2(bucketName)) {
            throw new RuntimeException("Bucket already exists. Please try a different name.");
        } else {
            try {
                bucket = amazonS3.createBucket(bucketName);
            } catch (AmazonS3Exception e) {
                throw new AmazonS3Exception(e.getMessage());
            }
        }
        return bucket;
    }

    public BucketsDTO listAllBuckets(){
        return new BucketsDTO(amazonS3.listBuckets());
    }

    public PutObjectResult uploadFile(MultipartFile file) throws IOException {

        String key = UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path tempFile = Files.createTempFile(file.getOriginalFilename(), ".mp4");
        Files.write(tempFile, file.getBytes());

        PutObjectRequest putObjectRequest = new PutObjectRequest(
                cliprBucketName,
                key,
                tempFile.toFile()
        );

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.addUserMetadata("key", key);

        putObjectRequest.setMetadata(objectMetadata);
        PutObjectResult putObjectResult = amazonS3.putObject(putObjectRequest);

        Files.delete(tempFile);
        return putObjectResult;

    }

    public S3ObjectsDTO getBucketObjects(String bucketName) {
        return new S3ObjectsDTO(amazonS3.listObjectsV2(bucketName).getObjectSummaries());
    }


}
