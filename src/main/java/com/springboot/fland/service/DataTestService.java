package com.springboot.fland.service;

import com.springboot.fland.data.dto.DataTestDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface DataTestService {
    DataTestDto DataTest(MultipartFile image, String result_Text, String score);
}
