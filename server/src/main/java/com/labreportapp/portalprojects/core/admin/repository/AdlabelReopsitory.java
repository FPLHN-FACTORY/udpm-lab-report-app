package com.labreportapp.portalprojects.core.admin.repository;

import com.labreportapp.portalprojects.core.admin.model.request.AdFindLabelRequest;
import com.labreportapp.portalprojects.core.admin.model.response.AdLabelReponse;
import com.labreportapp.portalprojects.repository.LabelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author NguyenVinh
 */
@Repository
public interface AdlabelReopsitory extends LabelRepository {

    @Query(value = """
             SELECT ROW_NUMBER() OVER(ORDER BY lab.last_modified_date DESC ) AS STT ,
                  lab.id,
                  lab.code,
                  lab.name,
                  lab.color_label
             FROM label lab 
             WHERE  
             ( :#{#rep.code} IS NULL 
                OR :#{#rep.code} LIKE '' 
                OR lab.code LIKE %:#{#rep.code}% )
             AND 
             ( :#{#rep.name} IS NULL 
                OR :#{#rep.name} LIKE '' 
                OR lab.name LIKE %:#{#rep.name}% )      
            """, countQuery = """
            SELECT COUNT(lab.id) FROM label lab 
             WHERE  
             ( :#{#rep.code} IS NULL 
                OR :#{#rep.code} LIKE '' 
                OR lab.code LIKE %:#{#rep.code}% )
             AND 
             ( :#{#rep.name} IS NULL 
                OR :#{#rep.name} LIKE '' 
                OR lab.name LIKE %:#{#rep.name}% )
            ORDER BY lab.last_modified_date DESC
            """, nativeQuery = true)
    Page<AdLabelReponse> findByNameLabel(@Param("rep") AdFindLabelRequest rep, Pageable page);

    @Query(value = """
            SELECT lab.code FROM label lab  WHERE lab.code = :ma
            """,nativeQuery = true)
    String getMalabel(@Param("ma") String ma);

    @Query(value = """
            SELECT lab.id FROM label lab  WHERE :status IS NOT NULL
            """,nativeQuery = true)
    List<String> getAllIdByStatus (@Param("status") String status);

    @Query("SELECT lab.id FROM Label lab WHERE lab.code = :code AND lab.id <> :id")
    String findByCodeLabel (@Param("code") String codeLabel ,
                            @Param("id") String id);
}
