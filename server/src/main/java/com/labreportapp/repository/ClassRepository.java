package com.labreportapp.repository;

import com.labreportapp.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(ClassRepository.NAME)
public interface ClassRepository extends JpaRepository<Class, String> {

    String NAME = "BaseClassRepository";
}
