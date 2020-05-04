package com.brain1.masterdata.rests;

import javax.annotation.Nonnull;

import com.brain1.masterdata.models.Tag;
import com.brain1.masterdata.repos.TagRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
@CrossOrigin(origins = { "http://localhost:3000", "https://brainmatter.xyz" })
public class TagRest {
    @Autowired
    TagRepo tagRepo;

    @GetMapping("/{topicName}")
    public Iterable<Tag> getByTopic(@PathVariable @Nonnull String topicName) {
        System.out.println(topicName);
        return tagRepo.findByTopicsTopicName(topicName);
    }

}