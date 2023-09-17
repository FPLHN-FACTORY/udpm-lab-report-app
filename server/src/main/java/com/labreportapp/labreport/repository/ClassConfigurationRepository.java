package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.entity.ClassConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(ClassConfigurationRepository.NAME)
public interface ClassConfigurationRepository extends JpaRepository<ClassConfiguration, String> {

    String NAME = "BaseClassConfigurationRepository";
}
