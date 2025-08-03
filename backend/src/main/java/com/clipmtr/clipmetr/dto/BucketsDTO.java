package com.clipmtr.clipmetr.dto;

import com.amazonaws.services.s3.model.Bucket;

import java.util.List;

public record BucketsDTO(List<Bucket> buckets) {

}
