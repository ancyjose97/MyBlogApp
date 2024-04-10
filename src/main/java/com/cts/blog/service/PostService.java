package com.cts.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cts.blog.dto.PostDto;


public interface PostService {

	PostDto createPost(PostDto postDto);

List<PostDto> getAllPosts();

PostDto getPostById(Long id);

PostDto updatePost(PostDto postDto, Long id);

void deletePostById(long id);

List<PostDto> getPostsByCategory(Long categoryId);
	
}
