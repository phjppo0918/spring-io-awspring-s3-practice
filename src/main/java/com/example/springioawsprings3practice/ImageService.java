package com.example.springioawsprings3practice;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    ImageRepository imageRepository;
    public String create(MultipartFile file) {
        return imageRepository.save(file);
    }
}
