package com.cts.blog.service;

import java.util.List;

import com.cts.blog.dto.CategoryDto;

public interface CategoryService {

	 CategoryDto addCategory(CategoryDto categoryDto);
	 
	 CategoryDto getCategory(Long categoryId);
	 
	List<CategoryDto> getAllCategories();
	 
	CategoryDto updateCategory(CategoryDto categoryDto, Long id);
	

	void deleteCategory(Long id);
	
}
