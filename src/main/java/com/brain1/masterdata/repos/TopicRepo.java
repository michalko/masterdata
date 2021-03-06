package com.brain1.masterdata.repos;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.brain1.masterdata.models.Topic;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TopicRepo extends CrudRepository<Topic, Long> {
    @Transactional
    @Modifying
    @Query("update Topic t set t.activeCurrently = t.activeCurrently + 1 where t.name in (?1)")
    void incrementActiveForTopics(List<String> names);

    Optional<Topic> findByName(String name);
}