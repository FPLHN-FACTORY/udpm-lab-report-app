package com.labreportapp.portalprojects.core.admin.repository;

import com.labreportapp.portalprojects.core.admin.model.request.AdFindCategoryRequest;
import com.labreportapp.portalprojects.core.admin.model.response.AdCategoryPesponse;
import com.labreportapp.portalprojects.entity.Category;
import com.labreportapp.portalprojects.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdCategoryRepository extends CategoryRepository {

    @Query(" SELECT cate FROM Category cate")
    List<Category> getAllCategory(Pageable pageable);

    @Query(value = """
             SELECT  cate.code                  
             FROM category cate 
             WHERE 
                  cate.code = :cateCode  
            """, nativeQuery = true)
    String findCateByCode(@Param("cateCode") String cateCode);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY cate.last_modified_date DESC ) AS STT ,
                    cate.id,
                    cate.code,
                    cate.name
            FROM category cate 
            WHERE  ( :#{#adFindCategoryRequest.name} IS NULL 
                   OR :#{#adFindCategoryRequest.name} LIKE '' 
                   OR cate.name LIKE %:#{#adFindCategoryRequest.name}% )              
                    """, countQuery = """    
            SELECT COUNT(cate.id) 
            FROM category cate 
            WHERE   ( :#{#adFindCategoryRequest.name} IS NULL 
                    OR :#{#adFindCategoryRequest.name} LIKE '' 
                    OR cate.name LIKE %:#{#adFindCategoryRequest.name}% )                     
            ORDER BY cate.last_modified_date DESC
            """, nativeQuery = true)
    Page<AdCategoryPesponse> searchCategory(@Param("adFindCategoryRequest") AdFindCategoryRequest adFindCategoryRequest, Pageable page);

    @Query(" SELECT cate FROM Category cate ")
    List<Category> getAllByIdCate();
}
