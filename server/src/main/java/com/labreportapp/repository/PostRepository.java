package com.labreportapp.repository;

import com.labreportapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository
public interface PostRepository extends JpaRepository<Post, String> {
}
