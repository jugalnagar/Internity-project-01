package com.Internity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Internity.model.Post;
import com.Internity.model.User;
import com.Internity.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;
	
	public Post createNewPost(Post post) {
		return postRepository.save(post);
	}
	
	public List<Post> fetchAllPostOfUser(User user) {
		return postRepository.findAllByUser(user);
	}
	
	public Post fetchSinglePostOfUserByPostId(long postId,User user) {
		return postRepository.findByPostIdAndUser(postId,user);
	}
	
	public long count() {
		return postRepository.count();
	}
}
