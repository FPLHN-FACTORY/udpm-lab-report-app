package com.labreportapp.labreport.core.admin.repository;

import com.labreportapp.labreport.core.admin.model.response.AdClassConfigurationResponse;
import com.labreportapp.labreport.repository.ClassConfigurationRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdClassConfigurationRepository extends ClassConfigurationRepository {
    @Query(value = """
                    select cc.id,
                    cc.class_size_min,
                    cc.class_size_max, 
                    cc.point_min, 
                    cc.maximum_number_of_breaks,
                    cc.number_honey
                    from class_configuration cc
            """, nativeQuery = true)
    AdClassConfigurationResponse getAllClassConfiguration();
}
