package com.Internity.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Internity.component.FileUploadHelper;
import com.Internity.model.Post;
import com.Internity.model.User;
import com.Internity.service.PostService;
import com.Internity.service.UserService;

import io.swagger.annotations.ApiOperation;

@Validated
@RestController
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileUploadHelper fileUploadHelper;
	
	
	@ApiOperation(value = "Upload new Post of Existing user")
	@PostMapping(path="/{mobile}")
	public ResponseEntity<Object> createNewPost(@RequestBody Post post,@PathVariable("mobile") long mobile) {
		
		
		User userFetch = null;
		try {
			userFetch = userService.findByMobile(mobile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String filename = userFetch.getLastName()+userFetch.getFirstName()+postService.count();
			String path = fileUploadHelper.uploadEncodedImage(post.getPostPic(), filename);
			if(path==null) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
			}
			post.setPostPic(path);
			post.setUser(userFetch);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Post Photo path is not correct");
		}
		
		try {
			post = postService.createNewPost(post);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Post Uploadation Failed!!!!!!");
		}
		
		return ResponseEntity.ok(post);
	}
	
	@ApiOperation(value = "Get all post Of Existing User")
	@GetMapping("/{mobile}") 
	public ResponseEntity<Object> fetchAllPostOfUser(@PathVariable("mobile")long mobile){
		
		User userFetch = null;
		try{
			userFetch = userService.findByMobile(mobile);
		}catch (Exception e) {
			throw new NoSuchElementException("User Not Exist");
		}
		
		
		List<Post> posts = null;
		
		try {
			posts = postService.fetchAllPostOfUser(userFetch);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Post not exist for particular user");
		}
		
		return ResponseEntity.ok(posts);
	}
	
	@ApiOperation(value = "Get Single post by PostId Of Existing User")
	@GetMapping("/{mobile}/{postId}") 
	public ResponseEntity<Object> fetchPostOfUser(@PathVariable("mobile")long mobile,@PathVariable("postId")long postId){
		
		User userFetch = null;
		
		try{
			userFetch = userService.findByMobile(mobile);
		} catch (Exception e) {
			throw new NoSuchElementException("User not registered");
		}
		
		Post post = postService.fetchSinglePostOfUserByPostId(postId,userFetch);
		if(post==null) {
			throw new NoSuchElementException("Post didn't uploaded with Id "+postId);
		}
		
		return ResponseEntity.ok(post);
	}

}
