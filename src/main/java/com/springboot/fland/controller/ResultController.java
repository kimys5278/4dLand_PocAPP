package com.springboot.fland.controller;

import com.springboot.fland.data.dto.ResultResponseDto;
import com.springboot.fland.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @PostMapping("/upload")
    public ResponseEntity<ResultResponseDto> uploadFiles(
            @RequestPart("frontImage") MultipartFile frontImage,
            @RequestPart("backImage") MultipartFile backImage,
            @RequestPart("leftImage") MultipartFile leftImage,
            @RequestPart("rightImage") MultipartFile rightImage,
            @RequestPart("upImage") MultipartFile upImage) throws IOException {

        ResultResponseDto results = resultService.uploadImagesAndFetchResults(frontImage, backImage, leftImage, rightImage, upImage);
        return ResponseEntity.status(HttpStatus.OK).body(results);

    }


}
