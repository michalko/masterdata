package com.brain1.masterdata.repos;

import com.brain1.masterdata.models.Post;

import org.springframework.data.repository.CrudRepository;

public interface PostRepo extends CrudRepository<Post, String> {
}