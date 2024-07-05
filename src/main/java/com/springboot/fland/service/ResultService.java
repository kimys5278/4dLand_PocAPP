package com.springboot.fland.service;

import com.springboot.fland.data.dto.ResultResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ResultService {
    ResultResponseDto uploadImagesAndFetchResults(MultipartFile frontImage, MultipartFile backImage, MultipartFile leftImage, MultipartFile rightImage, MultipartFile upImage) throws IOException;
}
