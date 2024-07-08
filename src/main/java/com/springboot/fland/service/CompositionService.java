package com.springboot.fland.service;

import com.springboot.fland.data.dto.CompositionResponseDto;
import com.springboot.fland.data.dto.ResultResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CompositionService {
    CompositionResponseDto uploadImageCompositionPlayResult(MultipartFile composition_image)throws IOException;
}
