package com.icet.shopeagle.service.image;

import com.icet.shopeagle.dto.ImageDto;
import com.icet.shopeagle.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file,Long imageId);
}
