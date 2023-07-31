package com.labreportapp.repository;

import com.labreportapp.entity.Evident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(EvidentRepository.NAME)
public interface EvidentRepository extends JpaRepository<Evident, String> {

    String NAME = "BaseEvidentRepository";
}
