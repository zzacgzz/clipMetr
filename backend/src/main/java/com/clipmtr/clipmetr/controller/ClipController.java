package com.clipmtr.clipmetr.controller;


import com.clipmtr.TestApi;
import com.clipmtr.openapi.model.TestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@RestController
public class ClipController implements TestApi {


    @Override
    public ResponseEntity<TestDto> test() {
        return new ResponseEntity<>(new TestDto().message("hello"), HttpStatus.OK);
    }
}
