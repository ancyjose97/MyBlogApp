package com.cts.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "comments")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//Primary key generation strategy/method
	private Long id;

	//field names are stored as column names in DB if @Column isn't specified
	private String name;
	private String email;
	private String body;

	//Lazy loads post data when needed
	//Eager loads data from DB along with other fields
	@ManyToOne(fetch = FetchType.LAZY)
	//foreign key
	@JoinColumn(name = "post_id",nullable = false)
	private Post post;

}
