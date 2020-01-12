package com.brain2.demo.rests;

import java.util.List;
import java.util.Optional;

import com.brain2.demo.models.Topic;
import com.brain2.demo.models.Topictags;
import com.brain2.demo.repos.TopicRepo;
import com.brain2.demo.repos.TopicTagsRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topics")
@CrossOrigin(origins = "http://localhost:3000")
public class TopicRest {

    @Autowired
    TopicRepo topicRepo;

    @Autowired
    TopicTagsRepo topicTagsRepo;

    @GetMapping
    public Iterable<Topic> getAll() {
        return topicRepo.findAll();
    }

    @GetMapping("/get/{id}")
    public Optional<Topic> getTopic(@PathVariable Long id) {
        return topicRepo.findById(id);
    }

    
    @GetMapping("/topicsWithTags")
    public Iterable<Topictags> getTopicsWithTags() {
        return topicTagsRepo.findAll();
    }

    @GetMapping("/topicsWithTagsFor")
    public List<Topictags> getTopicsWithTagsFor() {
        return topicTagsRepo.findByTopic_Name("Java");
    }

}