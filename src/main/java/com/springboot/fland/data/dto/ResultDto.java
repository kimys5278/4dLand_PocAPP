package com.springboot.fland.data.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDto {
    private String name;
    private String accuracy;
    private String rate;
    private String imageUrl;
}