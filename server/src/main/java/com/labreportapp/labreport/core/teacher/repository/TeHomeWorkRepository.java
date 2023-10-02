package com.labreportapp.labreport.core.teacher.repository;

import com.labreportapp.labreport.entity.HomeWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author hieundph25894
 */
public interface TeHomeWorkRepository extends JpaRepository<HomeWork, String> {

//    @Query(value = """
//            SELECT * FROM homework WHERE :id id = :id
//            """, nativeQuery = true)
//    Optional<HomeWork> findHomeWorkById(@Param("id") String id);
}
