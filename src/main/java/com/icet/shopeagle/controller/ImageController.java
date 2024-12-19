package com.icet.shopeagle.controller;

import com.icet.shopeagle.dto.ImageDto;
import com.icet.shopeagle.exception.ResourceNotFoundException;
import com.icet.shopeagle.model.Image;
import com.icet.shopeagle.response.ApiResponse;
import com.icet.shopeagle.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveimages(List<MultipartFile> files, @RequestParam Long productId){
        try {
            List<ImageDto> imageDtos=imageService.saveImage(files,productId);
            return ResponseEntity.ok(new ApiResponse("Upload Success",imageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload faild",e.getMessage()));
        }
    }

    @GetMapping("image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@RequestParam Long imageId) throws SQLException {
        Image image=imageService.getImageById(imageId);
        ByteArrayResource resource=new ByteArrayResource(image.getImage().getBytes(1,(int)image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+image.getFileName()+"\"").body(resource);
    }

    @PutMapping("image/download/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId,@RequestBody MultipartFile file){
        try {
            Image image=imageService.getImageById(imageId);
            if(image!=null){
                imageService.updateImage(file, imageId);
            }
            return ResponseEntity.ok(new ApiResponse("Upload Success",null ));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Upload failed!",INTERNAL_SERVER_ERROR));
        }
    }
    @DeleteMapping("image/download/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId,@RequestBody MultipartFile file){
        try {
            Image image=imageService.getImageById(imageId);
            if(image!=null){
                imageService.deleteImageById( imageId);
            }
            return ResponseEntity.ok(new ApiResponse("Delete Success",null ));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Delete failed!",INTERNAL_SERVER_ERROR));
        }
    }
}
