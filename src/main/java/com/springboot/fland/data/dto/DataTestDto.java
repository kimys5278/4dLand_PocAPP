package com.springboot.fland.data.dto;

import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataTestDto {
    private String result_text;
    private String accuracy;
    private String image_Url;
    private String score;


}
