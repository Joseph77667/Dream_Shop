package com.Joseph.dreamShop.controller;

import com.Joseph.dreamShop.dto.ImageDto;
import com.Joseph.dreamShop.exception.ResourceNotFoundException;
import com.Joseph.dreamShop.model.Image;
import com.Joseph.dreamShop.reponse.ApiResponse;
import com.Joseph.dreamShop.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService iImageService;

    public ImageController(IImageService iImageService) {
        this.iImageService = iImageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImage(@RequestParam List<MultipartFile> files, @RequestParam long prodId){
        try {
            List<ImageDto> imageDtos = iImageService.saveImages(files, prodId);
            return ResponseEntity.ok(new ApiResponse("Uploaded successfully", imageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed", e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable long imageId) throws SQLException {
        Image image = iImageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable long imageId, @RequestParam MultipartFile file){
        try {
            Image image = iImageService.getImageById(imageId);
            if (image != null) {
                iImageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Updated", null));
            }
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Image not found", null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed", INTERNAL_SERVER_ERROR));

    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable long imageId){
        try {
            Image image = iImageService.getImageById(imageId);
            if (image != null) {
                iImageService.deleteImageById( imageId);
                return ResponseEntity.ok(new ApiResponse("Deleted", null));
            }
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Image not found", null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed", INTERNAL_SERVER_ERROR));

    }


}
