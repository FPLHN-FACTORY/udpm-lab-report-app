package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.entity.HomeWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(HomeWorkRepository.NAME)
public interface HomeWorkRepository extends JpaRepository<HomeWork, String> {

    String NAME = "BaseHomeWorkRepository";
}
