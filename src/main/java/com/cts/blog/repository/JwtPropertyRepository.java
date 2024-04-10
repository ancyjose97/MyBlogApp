package com.cts.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.blog.entity.JwtProperty;

public interface JwtPropertyRepository extends JpaRepository<JwtProperty, Long> {

	JwtProperty findByPropertyValue(String propertyValue);
}
