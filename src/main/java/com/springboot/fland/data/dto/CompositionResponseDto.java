package com.springboot.fland.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompositionResponseDto {
    @JsonProperty("image_url")
    private String imageUrl;
}
