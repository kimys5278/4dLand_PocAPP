package com.springboot.fland.service.Impl;

import com.springboot.fland.S3.S3Uploader;
import com.springboot.fland.data.repository.ResultRepository;
import com.springboot.fland.data.dto.ResultDto;
import com.springboot.fland.data.dto.ResultResponseDto;
import com.springboot.fland.data.entity.Result;
import com.springboot.fland.service.ResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final S3Uploader s3Uploader;
    private final WebClient webClient;

    public ResultServiceImpl(S3Uploader s3Uploader, WebClient.Builder webClientBuilder, ResultRepository resultRepository) {
        this.s3Uploader = s3Uploader;
        this.webClient = webClientBuilder.baseUrl("http://localhost:8000").build();
        this.resultRepository = resultRepository;
    }

    @Override
    public ResultResponseDto uploadImagesAndFetchResults(MultipartFile frontImage, MultipartFile backImage, MultipartFile leftImage, MultipartFile rightImage, MultipartFile upImage) throws IOException {
        MultipartFile[] files = {frontImage, backImage, leftImage, rightImage, upImage};
        List<Result> results = new ArrayList<>();
        ResultResponseDto resultResponseDto = null;

        try {
            // 각 파일을 S3에 업로드하고 URL을 가져옵니다.
            List<String> imageUrls = new ArrayList<>();
            for (MultipartFile file : files) {
                String imageUrl = s3Uploader.uploadImage(file, "images/before");
                imageUrls.add(imageUrl);
            }

            // 이미지 URL을 FastAPI로 전송합니다.
            Mono<ResultResponseDto> response = webClient.post()
                    .uri("/predict_images")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(imageUrls)
                    .retrieve()
                    .bodyToMono(ResultResponseDto.class);

            resultResponseDto = response.block();

            if (resultResponseDto != null) {
                for (ResultDto dto : resultResponseDto.getResults()) {
                    Result result = new Result();
                    result.setName(dto.getName());
                    result.setAccuracy(dto.getAccuracy());
                    result.setRate(dto.getRate());
                    result.setImage_Url(dto.getImageUrl());

                    resultRepository.save(result);
                    results.add(result);
                }
            }
        } catch (WebClientResponseException | IOException e) {
            log.error("Error occurred while fetching results: ", e);
        }
        return resultResponseDto;
    }
}
