package com.brain2.demo.repos;

import com.brain2.demo.models.Post;

import org.springframework.data.repository.CrudRepository;

public interface PostRepo extends CrudRepository<Post, Long> {

}