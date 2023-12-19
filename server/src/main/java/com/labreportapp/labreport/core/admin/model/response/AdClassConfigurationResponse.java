package com.labreportapp.labreport.core.admin.model.response;
import com.labreportapp.labreport.entity.ClassConfiguration;
import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {ClassConfiguration.class})
public interface AdClassConfigurationResponse extends IsIdentified {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.class_size_min}")
    Integer getClassSizeMin();

    @Value("#{target.class_size_max}")
    Integer getClassSizeMax();

    @Value("#{target.point_min}")
    Double getPointMin();

    @Value("#{target.maximum_number_of_breaks}")
    Double getMaximumNumberOfBreaks();

    @Value("#{target.number_honey}")
    Integer getNumberHoney();

    @Value("#{target.number_class_max}")
    Integer getNumberClassMax();

}
