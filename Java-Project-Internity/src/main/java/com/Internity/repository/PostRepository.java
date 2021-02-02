package com.Internity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Internity.model.Post;
import com.Internity.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{
	
	public List<Post> findAllByUser(User user);
	
	public Post findByPostIdAndUser(long postId,User user);

}
