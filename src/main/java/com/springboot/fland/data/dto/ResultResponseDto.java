package com.springboot.fland.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResultResponseDto {
    private int count;
    private List<ResultDto> results;
}