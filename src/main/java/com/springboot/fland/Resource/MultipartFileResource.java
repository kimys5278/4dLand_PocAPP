package com.springboot.fland.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public class MultipartFileResource extends ByteArrayResource {

    private final String filename;

    public MultipartFileResource(MultipartFile multipartFile) throws IOException {
        super(multipartFile.getBytes());
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            this.filename = "default_filename.ext"; // 기본 파일 이름 설정
        } else {
            this.filename = originalFilename;
        }
    }

    @Override
    public String getFilename() {
        return this.filename; // 파일 이름 반환
    }

    @Override
    public long contentLength() {
        return this.getByteArray().length; // 파일 길이 반환
    }
}
