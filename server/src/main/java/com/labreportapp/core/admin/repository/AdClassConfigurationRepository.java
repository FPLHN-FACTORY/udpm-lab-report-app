package com.labreportapp.core.admin.repository;

import com.labreportapp.core.admin.model.response.AdClassConfigurationResponse;
import com.labreportapp.repository.ClassConfigurationRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdClassConfigurationRepository extends ClassConfigurationRepository {
    @Query(value = """
                    select cc.id, cc.class_size_max from class_configuration cc
            """, nativeQuery = true)
    List<AdClassConfigurationResponse> getAllClassConfiguration();
}
