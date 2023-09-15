package com.labreportapp.core.student.model.response;

import com.labreportapp.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

public interface StClassConfigurationResponse extends IsIdentified {

  @Value("#{target.class_size_max}")
  Integer getClassSizeMax();

}
