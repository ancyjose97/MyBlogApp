package com.cts.blog.service;

import java.util.List;

import com.cts.blog.dto.CommentDto;

public interface CommentService {

	//creating comment for a post with post id.
	CommentDto createComment(long postId, CommentDto commentDto);
	
	List<CommentDto>  getAllCommentsByPostId(long postId);
	
	CommentDto getCommentById(Long postId, Long commentId);
	
	CommentDto updateComment(Long postId,Long commentId, CommentDto commentDto);
	
	void deleteComment(Long postId, Long commentId);
}
