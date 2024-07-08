package com.springboot.fland.data.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Composition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPOSITION_PLAY_ID")
    private Long id;

    @Transient // 데이터베이스에 저장하지 않음
    private MultipartFile composition_image;

    @Column(name = "COMPOSITION_IMAGE_URL")
    private String imageUrl;

}
