package com.springboot.fland.data.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="COMBINATION_NAME")
    private String name;

    @Column(name="COMBINATION_COUNT")
    private int count;

    private String accuracy;

    private String rate;

    @Transient // 데이터베이스에 저장하지 않음
    private MultipartFile front_image;

    @Transient // 데이터베이스에 저장하지 않음
    private MultipartFile back_image;

    @Transient // 데이터베이스에 저장하지 않음
    private MultipartFile left_image;

    @Transient // 데이터베이스에 저장하지 않음
    private MultipartFile right_image;

    @Transient // 데이터베이스에 저장하지 않음
    private MultipartFile up_image;


    private String image_Url;
}
