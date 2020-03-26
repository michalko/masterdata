package com.brain2.demo.repos;

import java.util.ArrayList;
import java.util.Set;

import com.brain2.demo.models.Post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PostRepo extends CrudRepository<Post, String> {

    @Query(value = "SELECT * FROM Post p where p.topic_name = ?1 and p.id not in (?3) ORDER BY ABS(correct_precent - ?2) LIMIT 40", nativeQuery = true)
    ArrayList<Post> findRandomsWithinCorrectRatio(String topic, Integer correctPrecent, Set<String> ids);

    @Query(value = "SELECT * FROM Post p where p.topic_name = ?1 and p.id not in (?3) and p.tags in (?4) ORDER BY ABS(correct_precent - ?2) LIMIT 40", nativeQuery = true)
    ArrayList<Post> findRandomsWithinCorrectRatioWithTags(String topic, Integer correctPrecent, Set<String> ids,
            Set<String> tags);

    @Query("SELECT p.realPostsInTopics FROM Post p where p.topic = ?1 and correctPrecent between ?2 and ?3 order by function('RAND')")
    Integer findRandomWithCorrectRatioBetween(String topic, Integer correctPrecentLow, Integer correctPrecentUp);
}