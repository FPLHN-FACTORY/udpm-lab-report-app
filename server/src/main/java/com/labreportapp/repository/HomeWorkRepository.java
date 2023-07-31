package com.labreportapp.repository;

import com.labreportapp.entity.HomeWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(HomeWorkRepository.NAME)
public interface HomeWorkRepository extends JpaRepository<HomeWork, String> {

    String NAME = "BaseHomeWorkRepository";
}
