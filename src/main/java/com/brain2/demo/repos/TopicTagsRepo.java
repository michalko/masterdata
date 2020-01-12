package com.brain2.demo.repos;

import java.util.List;

import com.brain2.demo.models.Topictags;

import org.springframework.data.repository.CrudRepository;

public interface TopicTagsRepo extends CrudRepository<Topictags, Long> {
    List<Topictags> findByTopic_Name(String name);
}