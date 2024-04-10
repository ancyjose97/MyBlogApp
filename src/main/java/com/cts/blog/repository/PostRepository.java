package com.cts.blog.repository;

import java.util.List;

import org.hibernate.annotations.DialectOverride.Where;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.blog.entity.Post;

import jakarta.persistence.criteria.From;

public interface PostRepository extends JpaRepository<Post, Long> {

	
	List<Post> findByCategoryId(Long categoryId);
}
