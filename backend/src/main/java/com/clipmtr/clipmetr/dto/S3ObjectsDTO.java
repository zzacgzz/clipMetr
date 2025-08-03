package com.clipmtr.clipmetr.dto;

import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.List;

public record S3ObjectsDTO(List<S3ObjectSummary> objectSummaries) {
}
