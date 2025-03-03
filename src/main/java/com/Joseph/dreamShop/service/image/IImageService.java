package com.Joseph.dreamShop.service.image;

import com.Joseph.dreamShop.dto.ImageDto;
import com.Joseph.dreamShop.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(long id);
    void deleteImageById(long id);
    List<ImageDto> saveImages(List<MultipartFile> files, long prodId);
    void updateImage(MultipartFile file, long imageId);
}
