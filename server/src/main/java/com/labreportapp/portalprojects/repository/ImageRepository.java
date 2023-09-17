package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(ImageRepository.NAME)
public interface ImageRepository extends JpaRepository<Image, String> {

    public static final String NAME = "BaseImageRepository";
}
