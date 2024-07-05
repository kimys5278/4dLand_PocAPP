package com.springboot.fland.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/fdLand")
public class FileUploadController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    private Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @PostMapping("/predict-results")
    public ResponseEntity<String> uploadFile(@RequestParam("image") MultipartFile file) {
        String filename = file.getOriginalFilename();

        webClientBuilder.build()
                .post()
                .uri("http://127.0.0.1:8000/predict")
                .bodyValue(file.getResource())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String result = webClientBuilder.build()
                .get()
                .uri("http://127.0.0.1:8000/results/" + filename)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        logger.info("result : {}",result);


        return ResponseEntity.ok(result);
    }
}
