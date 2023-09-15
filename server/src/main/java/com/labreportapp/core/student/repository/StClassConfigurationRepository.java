package com.labreportapp.core.student.repository;

import com.labreportapp.core.student.model.response.StClassConfigurationResponse;
import com.labreportapp.repository.ClassConfigurationRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StClassConfigurationRepository extends ClassConfigurationRepository {

  @Query(value = "SELECT class_size_max FROM class_configuration LIMIT 1", nativeQuery = true)
  StClassConfigurationResponse getClassConfiguration();

}
