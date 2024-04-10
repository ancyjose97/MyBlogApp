package com.cts.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.cts.blog.dto.CommentDto;
import com.cts.blog.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") Long postId, @Valid @RequestBody CommentDto commentDto) {
		
		return new ResponseEntity<CommentDto>(commentService.createComment(postId, commentDto),HttpStatus.CREATED);
	}
	
	@GetMapping("/posts/{postId}/comments")
	public ResponseEntity<List<CommentDto>> getAllCommentsByPostId(@PathVariable(name = "postId") Long postId) {
		
		return ResponseEntity.ok(commentService.getAllCommentsByPostId(postId));
	}
	
	
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId, @PathVariable (value = "commentId") Long commentId) {
		
		return new ResponseEntity<>(commentService.getCommentById(postId, commentId),HttpStatus.OK);
	}
	
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") Long postId, @PathVariable(value = "commentId") Long commentId, @Valid @RequestBody CommentDto commentDto) {
		
		
		return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDto), HttpStatus.OK);
	}
	
	
	@DeleteMapping("/posts/{postId}/comments/{id}")
	
	//value or name is the alias name in @Pathvariable
	//1st argument has no value because parameter name in URL and parameter name in method Signature are same.
	//2nd argument has value because  parameter name in URL and parameter name in method Signature are different.
	
	public ResponseEntity<String> deleteComment (@PathVariable Long postId, @PathVariable(value = "id") Long commentId) {
		
		commentService.deleteComment(postId, commentId);
		
		return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
		
	}
}
