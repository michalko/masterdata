package com.brain2.demo.repos;

import com.brain2.demo.models.Post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PostRepo extends CrudRepository<Post, Long> {

    @Query("SELECT p.realPostsInTopics FROM Post p where p.topic = ?1 and correctPrecent between ?2 and ?3 order by function('RAND')")
    Integer findRandomWithCorrectRatioBetween(String topic, Integer correctPrecentLow, Integer correctPrecentUp);
}