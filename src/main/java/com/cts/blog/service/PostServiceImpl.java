package com.cts.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.blog.dto.PostDto;
import com.cts.blog.entity.Category;
import com.cts.blog.entity.Post;
import com.cts.blog.exception.ResourceNotFoundException;
import com.cts.blog.repository.CategoryRepository;
import com.cts.blog.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	//CONSTRUCTOR BASED INJECTION

	/*
	 * public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
	 *  
	 * this.postRepository = postRepository; 
	 * this.modelMapper = modelMapper;
	 * 
	 * }
	 * 
	 * 
	 */

	@Override
	public PostDto createPost(PostDto postDto) {
		
		
		//retrieving category class details by category id 
		//retrieving the category which the user selected while creating post

		Category category =	categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		
		
		

		// Saving newly created post details to repo
		// i.e. converting Dto to entity

		//Post post = maptoEntity(postDto);
		/*
		 * Post newPost = new Post(); newPost.setTitle(postDto.getTitle());
		 * newPost.setContent(postDto.getContent());
		 * newPost.setDescription(postDto.getDescription());
		 */
		

		Post post = maptoEntity(postDto);
		
		/*
		 *  The following line is not needed.  maptoEntity(postDto) automatically does it.
		 *  
		 *   post.setCategory(category);
		 */
			
		
		Post newPost1 = postRepository.save(post);

		// converting Post to PostDto to return it.
		// i.e. converting entity to Dto

		/*
		 * PostDto postResponse = new PostDto();
		 * 
		 * postResponse.setId(newPost1.getId());
		 * postResponse.setContent(newPost1.getContent());
		 * postResponse.setDescription(newPost1.getDescription());
		 * postResponse.setTitle(newPost1.getTitle());
		 * 
		 */

		PostDto postDto1 = maptoDto(newPost1);

		return postDto1;
	}


	@Override
	public List<PostDto> getAllPosts() {

		List<Post> posts = postRepository.findAll();

		return posts.stream().map(post -> maptoDto(post)).collect(Collectors.toList());

	}

	@Override
	public PostDto getPostById(Long id) {

		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		return maptoDto(post);
	}

	// converting Dto to entity
	private Post maptoEntity(PostDto postDto) {

		
		Post newPost = modelMapper.map(postDto, Post.class);
		
		//INSTEAD OF WRITING THE BELOW LINES OF CODE AND MANUALLY CONVERTING / SETTING DTO CLASS MEMBERS TO ENTITY CLASS MEMBERS, WE CAN USE MODELMAPPER CLASS
		
		/*
		Post newPost = new Post();

		newPost.setTitle(postDto.getTitle());
		newPost.setContent(postDto.getContent());
		newPost.setDescription(postDto.getDescription());
		
		*/

		return newPost;
	}

	// converting entity to Dto
	private PostDto maptoDto(Post post) {

		PostDto postResponse = modelMapper.map(post, PostDto.class);
		
	
		
		/*
		PostDto postResponse = new PostDto();

		postResponse.setId(post.getId());
		postResponse.setContent(post.getContent());
		postResponse.setDescription(post.getDescription());
		postResponse.setTitle(post.getTitle());
		

		 */
		
		return postResponse;

	}

	// get the post information by id and update it
	@Override
	public PostDto updatePost(PostDto postDto, Long id) {

		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		
		//Retrieving the category which the user updated in already created post
	Category category =	categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());
		
		post.setCategory(category);

		postRepository.save(post);

		return maptoDto(post);
	}

	@Override
	public void deletePostById(long id) {

		// find post by id to delete
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		postRepository.delete(post);

	}


	@Override
	public List<PostDto> getPostsByCategory(Long categoryId) {
		
		
		//checking if category id exists 
		Category category =	categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));


	List<Post> posts = postRepository.findByCategoryId(categoryId);
		
		return posts.stream().map((post) -> maptoDto(post)).toList();
	}

}
