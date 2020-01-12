package com.brain2.demo.repos;

import com.brain2.demo.models.Topic;

import org.springframework.data.repository.CrudRepository;

public interface TopicRepo extends CrudRepository<Topic, Long> {


}