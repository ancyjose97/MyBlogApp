package com.cts.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

// @Data//Mandatory

//Here, @data is removed to neglect toString() method for ModelMapper class. Because getAllPosts() and getPostsById() methods of PostServiceImpl classes use ModelMapper in mapToDto() method.
// when @data is used, mapToDto returns null value when retrieving comments along with post.

@Getter
@Setter
@AllArgsConstructor //Mandatory
@NoArgsConstructor //Mandatory

@Entity
@Table(name = "posts", uniqueConstraints = { @UniqueConstraint(columnNames = { "title" }) })
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "content", nullable = false)
	private String content;

	@OneToMany(mappedBy = "post",//field name in Comment class
			cascade = CascadeType.ALL, //Whenever any change/operation is done is parent, the same operation is done in Child as well;
	//Eg: CascadeType.PERSIST => When we save the Post entity, the Comment entity will also get saved.

	orphanRemoval = true) //Whenever parent is removed, child is also removed.
	//CascadeType.REMOVE and orphanRemoval = true are almost same where orphanRemoval = true completely removes/ disconnects/deletes

	//Post is the owner & COmment is a dependent of Post
	
	//Set is declared as Many is used in annotation
	
	private Set<Comment> commentsSet = new HashSet<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;
}
