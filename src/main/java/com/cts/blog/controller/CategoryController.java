package com.cts.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.blog.dto.CategoryDto;
import com.cts.blog.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	
	private CategoryService categoryService;
	
	
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}


	//@Requestbody converts JSON object into a Java object
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
		
		
		return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
		
		
	}
	
	@GetMapping("/{id}") 
	public ResponseEntity<CategoryDto> getCategory(@PathVariable(value = "id") Long id) {
		
		return new ResponseEntity<CategoryDto>(categoryService.getCategory(id), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		
		return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Long id) {
		
		
		return new ResponseEntity<CategoryDto>(categoryService.updateCategory(categoryDto, id), HttpStatus.OK);
	
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId) {
		
		categoryService.deleteCategory(categoryId);
		
		return new ResponseEntity<>("Category deleted successfully",HttpStatus.OK);
		
	}
}

