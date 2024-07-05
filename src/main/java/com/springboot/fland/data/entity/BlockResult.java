package com.springboot.fland.data.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CollectionType;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BlockResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BLOCK_ID")
    private Long id;

    private String combination_name;

    private String accuracy;

    private String combination_count;

    @ElementCollection
    private List<String> bounding_box;

    @Transient
    @Column(name = "BLOCK_IMAGE")
    @JsonIgnore
    private MultipartFile image;

    @ElementCollection
    private List<String> image_Url;

}
