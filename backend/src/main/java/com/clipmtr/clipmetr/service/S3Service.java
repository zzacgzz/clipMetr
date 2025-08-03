package com.clipmtr.clipmetr.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.clipmtr.clipmetr.dto.BucketsDTO;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.model.Bucket;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class S3Service {

    private final AmazonS3 amazonS3;
    public S3Service() {
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .build();

    }


    public Bucket createBucket(String bucketName) {
        Bucket bucket = null;
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



}
