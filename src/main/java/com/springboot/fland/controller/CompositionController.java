package com.springboot.fland.controller;

import com.springboot.fland.data.dto.CompositionResponseDto;
import com.springboot.fland.data.dto.ResultResponseDto;
import com.springboot.fland.service.CompositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/composition")
@RequiredArgsConstructor
public class CompositionController {

    private final CompositionService compositionService;

    @PostMapping("/composition-upload")
    public ResponseEntity<CompositionResponseDto> uploadFiles(
            @RequestPart("composition_image") MultipartFile composition_image) throws IOException {

        CompositionResponseDto compositionResponseDto = compositionService.uploadImageCompositionPlayResult(composition_image);
        return ResponseEntity.status(HttpStatus.OK).body(compositionResponseDto);

    }

}
