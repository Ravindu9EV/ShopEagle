package com.icet.shopeagle.repository;

import com.icet.shopeagle.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
