package com.brain2.demo.repos;

import java.util.List;
import java.util.Optional;

import com.brain2.demo.models.TopicTags;

import org.springframework.data.repository.CrudRepository;

public interface TopicTagsRepo extends CrudRepository<TopicTags, Long> {
    List<TopicTags> findByTopic_Name(String name);

    Optional<TopicTags> findByTag_IdAndTopic_Id(Long tagID, Long topicID);
}