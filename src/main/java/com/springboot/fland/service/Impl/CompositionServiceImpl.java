package com.springboot.fland.service.Impl;

import com.springboot.fland.S3.S3Uploader;
import com.springboot.fland.data.dao.ResultDao;
import com.springboot.fland.data.dto.CompositionResponseDto;
import com.springboot.fland.data.entity.Composition;
import com.springboot.fland.data.repository.CompositionRepository;
import com.springboot.fland.service.CompositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
public class CompositionServiceImpl implements CompositionService {
    private final CompositionRepository compositionRepository;
    private final S3Uploader s3Uploader;
    private final WebClient webClient;
    private final ResultDao resultDao;

    public CompositionServiceImpl(CompositionRepository compositionRepository,
                                  S3Uploader s3Uploader, WebClient.Builder webClientBuilder,
                                  ResultDao resultDao) {
        this.compositionRepository = compositionRepository;
        this.s3Uploader = s3Uploader;
        this.webClient = webClientBuilder.baseUrl("http://localhost:8000").build();
        this.resultDao = resultDao;
    }

    @Override
    public CompositionResponseDto uploadImageCompositionPlayResult(MultipartFile composition_image) throws IOException {
        CompositionResponseDto compositionResponseDto = null;
        try {
            // 이미지 S3에 업로드
            String imageUrl = s3Uploader.uploadImage(composition_image, "/images/before");

            // 이미지 파일을 FastAPI로 전송
            Mono<CompositionResponseDto> response = webClient.post()
                    .uri("/composition")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData("file", composition_image.getResource()))
                    .retrieve()
                    .bodyToMono(CompositionResponseDto.class);

            compositionResponseDto = response.block();

            if (compositionResponseDto != null) {
                Composition composition = new Composition();
                composition.setImageUrl(compositionResponseDto.getImageUrl());
                resultDao.saveComposition(composition);
            }
        } catch (WebClientResponseException | IOException e) {
            log.error("Error occurred while fetching results: ", e);
        }
        return compositionResponseDto;
    }
}
