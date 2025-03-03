package com.Joseph.dreamShop.service.image;

import com.Joseph.dreamShop.dto.ImageDto;
import com.Joseph.dreamShop.exception.ResourceNotFoundException;
import com.Joseph.dreamShop.model.Image;
import com.Joseph.dreamShop.model.Product;
import com.Joseph.dreamShop.repository.ImageRepository;
import com.Joseph.dreamShop.repository.ProductRepository;
import com.Joseph.dreamShop.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final ProductService productService;

    public ImageService(ImageRepository imageRepository, ProductService productService) {
        this.imageRepository = imageRepository;
        this.productService = productService;
    }

    @Override
    public Image getImageById(long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image not found"));
    }

    @Override
    public void deleteImageById(long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,
                () -> new ResourceNotFoundException("Image not found"));
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, long prodId) {
        Product product = productService.getProductById(prodId);
        List<ImageDto> savedImageDtos = new ArrayList<>();
        for (MultipartFile file : files){
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String downloadUrl = "/api/v1/images/image/download/" + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);

                savedImage.setDownloadUrl("/api/v1/images/image/download/"+ savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDtos.add(imageDto);


            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        }
        catch (IOException | SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
