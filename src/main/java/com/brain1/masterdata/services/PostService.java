package com.brain1.masterdata.services;

import java.util.ArrayList;
import java.util.List;

import com.brain1.masterdata.models.Post;
import com.brain1.masterdata.models.Tag;
import com.brain1.masterdata.repos.TagRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    TopicService topicService;
    @Autowired
    TagRepo tagRepo;

    public void updateTagsInDb(final Post post, final Long topicID, final List<String> tags) {
        final List<Tag> tagsList = new ArrayList<Tag>();
        tags.forEach(tagName -> {
            System.out.println("finding by tag name" + tagsList);
            final var tag = tagRepo.findByName(tagName).orElseGet(() -> tagRepo.save(new Tag(tagName)));
            tagsList.add(tag);
            topicService.addTopicTag(topicID, post, tag);
        });
        post.setTags(tagsList);
    }
}