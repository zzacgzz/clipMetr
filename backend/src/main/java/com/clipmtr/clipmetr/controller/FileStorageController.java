package com.clipmtr.clipmetr.controller;


import com.amazonaws.Response;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.clipmtr.clipmetr.dto.BucketsDTO;
import com.clipmtr.clipmetr.service.S3Service;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "aws", description = "Amazon S3 service")
@RestController
public class FileStorageController {

    private final S3Service s3Service;

    public FileStorageController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @Operation(
            operationId = "Get all buckets",
            summary = "Get all buckets endpoint..",
            description = "s3 buckets",
            tags = {"aws"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BucketsDTO.class))}
                    )})
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/buckets",
            produces = {"application/json"}
    )
    ResponseEntity<BucketsDTO> buckets() {

        return new ResponseEntity<>(s3Service.listAllBuckets(), HttpStatus.OK);
    }

    @Operation(
            operationId = "Create new bucket",
            summary = "Creates a new bucket with a name",
            description = "New bucket",
            tags = {"aws"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Bucket.class))}),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Bucket exists",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RuntimeException.class))}),

                    @ApiResponse(
                            responseCode = "500",
                            description = "AWS Internal Error",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AmazonS3Exception.class))})
            }

    )
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/create/{bucketName}",
            produces = {"application/json"}
    )
    ResponseEntity<Bucket> create(@ApiParam(value = "", required = true) @PathVariable String bucketName) {

        return new ResponseEntity<>(s3Service.createBucket(bucketName), HttpStatus.OK);
    }



/*
Things the controller will be able to do:
-   receive video and upload to S3 bucket?
-   generate a unique id(uuid v7), with metadata
-   remove video from the s3 bucket
-   same video? don't add. but how do we know it's the same video?


 - on first upload of a video -  you store all of its data
 - on second upload of the same video - grab a random timeframe
   check to see if it's in the DB
 - for a requested time frame, store it incase a different user requests it.
 */
}
