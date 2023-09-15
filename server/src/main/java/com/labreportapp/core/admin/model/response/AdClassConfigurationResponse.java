package com.labreportapp.core.admin.model.response;
import com.labreportapp.entity.ClassConfiguration;
import com.labreportapp.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {ClassConfiguration.class})
public interface AdClassConfigurationResponse extends IsIdentified {
    @Value("#{target.id}")
    String getId();

    @Value("#{target.class_size_max}")
    String getClassSizeMax();
}
