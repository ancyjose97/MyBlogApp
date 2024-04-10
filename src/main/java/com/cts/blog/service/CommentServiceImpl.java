package com.cts.blog.service;

import java.util.List;

import java.util.stream.Collectors;

import org.hibernate.engine.query.spi.ReturnMetadata;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cts.blog.dto.CommentDto;
import com.cts.blog.entity.Comment;
import com.cts.blog.entity.Post;
import com.cts.blog.exception.BlogAPIException;
import com.cts.blog.exception.ResourceNotFoundException;
import com.cts.blog.repository.CommentRepository;
import com.cts.blog.repository.PostRepository;
import com.cts.blog.repository.UserRepository;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepository postRepository;
	
	
	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ModelMapper modelMapper;

	/*
	 * CONSTRUCTOR BASED INJECTION
	 * 
	 * Starting with Spring 4.3, if a class, which is configured as a Spring bean,
	 * has only one constructor, the @Autowired annotation can be omitted and Spring
	 * will use that constructor and inject all necessary dependencies.
	 * 
	 * 
	 * So need of @Autowired annotation here, for this constructor based injection
	 * 
	 * public CommentServiceImpl(PostRepository postRepository, CommentRepository
	 * commentRepository, ModelMapper modelMapper) {
	 * 
	 * this.postRepository = postRepository; this.commentRepository =
	 * commentRepository; this.modelMapper = modelMapper; }
	 * 
	 */

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {

	
		// find post by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// create comment in post. i.e save comment details in comment repo

		Comment comment = mapToEntity(commentDto);

		comment.setPost(post);

		Comment createComment = commentRepository.save(comment);

		return mapToDto(createComment);
	}

	public List<CommentDto> getAllCommentsByPostId(long postId) {

		List<Comment> comments = commentRepository.findByPostId(postId);

		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

	}

	private Comment mapToEntity(CommentDto commentDto) {
		
		
		Comment comment = modelMapper.map(commentDto, Comment.class);

//		Comment comment = new Comment();
//
//		comment.setName(commentDto.getName());
//		comment.setBody(commentDto.getBody());
//		comment.setEmail(commentDto.getEmail());

		return comment;
	}

	private CommentDto mapToDto(Comment comment) {

		CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
		
//		CommentDto commentDto = new CommentDto();
//
//		commentDto.setId(comment.getId());
//		commentDto.setBody(comment.getBody());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setName(comment.getName());

		return commentDto;
	}

	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {

		// get post by postId

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// get comment by commentId

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {

			throw new BlogAPIException("Comment do not belong to this post");
		}

		return mapToDto(comment);
	}

	@Override
	public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {

		// get post by postId

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// get comment by commentId

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		// check if the comment Id belongs to that post id

		if (!comment.getPost().getId().equals(post.getId())) {

			throw new BlogAPIException("Comment do not belong to this post");
		}

		// update comment in post

		comment.setName(commentDto.getName());
		comment.setBody(commentDto.getBody());
		comment.setEmail(commentDto.getEmail());

		Comment updatedComment = commentRepository.save(comment);

		return mapToDto(updatedComment);
	}

	@Override
	public void deleteComment(Long postId, Long commentId) {

		// get post by postId

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// get comment by commentId

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		// check if the comment Id belongs to that post id

		if (!comment.getPost().getId().equals(post.getId())) {

			throw new BlogAPIException("Comment do not belong to this post");
		}

		commentRepository.delete(comment);

	}

}
