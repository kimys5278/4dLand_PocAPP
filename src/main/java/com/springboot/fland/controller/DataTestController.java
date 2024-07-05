//package com.springboot.fland.controller;
//
//
//import com.springboot.fland.data.dto.DataTestDto;
//import com.springboot.fland.data.dto.ResponseDtoTest;
//
//import com.springboot.fland.service.DataTestService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/fdLand")
//@RequiredArgsConstructor
//public class DataTestController {
//
//    private final DataTestService dataTestService;
//    private final BlockTestService blockTestService;
//
//    @PostMapping(value = "/result", consumes = "multipart/form-data")
//    public ResponseEntity<DataTestDto> DataTest(MultipartFile image, String result_Text, String score){
//        DataTestDto dataTestDto = dataTestService.DataTest(image,result_Text,score);
//        return ResponseEntity.status(HttpStatus.OK).body(dataTestDto);
//    }
//
//    @PostMapping(value = "/predict-result", consumes = "multipart/form-data")
//    public ResponseEntity<ResponseDtoTest> BlockTest(@RequestParam("image") MultipartFile image) throws IOException {
//        ResponseDtoTest responseDto = blockTestService.BlockTest(image);
//        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
//    }
//
//
//
//
//}
//
//
