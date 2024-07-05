package com.springboot.fland.service.Impl;

import com.springboot.fland.S3.S3Uploader;
import com.springboot.fland.data.repository.ResultRepository;
import com.springboot.fland.data.dto.DataTestDto;
import com.springboot.fland.data.entity.Result;

import com.springboot.fland.service.DataTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DataTestServiceImpl implements DataTestService {

    private final ResultRepository resultRepository;
    private final S3Uploader s3Uploader;


    @Override
    public DataTestDto DataTest(MultipartFile image, String result_Text, String score) {

        DataTestDto dataTestDto = new DataTestDto();
        dataTestDto.setResult_text(result_Text);
        dataTestDto.setScore(score);

        try{
            String imageUrl = s3Uploader.uploadImage(image, "image");
            dataTestDto.setImage_Url(imageUrl);
        }catch (IOException e){
            e.printStackTrace();
        }

        Result result = new Result();
        result.setName(dataTestDto.getResult_text());
        result.setAccuracy(dataTestDto.getScore());
        result.setImage_Url(dataTestDto.getImage_Url());

        resultRepository.save(result);

        return dataTestDto;
    }


}
