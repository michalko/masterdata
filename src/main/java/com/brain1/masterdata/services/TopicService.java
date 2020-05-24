package com.brain1.masterdata.services;

import java.util.List;
import java.util.stream.Collectors;

import com.brain1.masterdata.models.Post;
import com.brain1.masterdata.models.Tag;
import com.brain1.masterdata.models.TopicTags;
import com.brain1.masterdata.models.TopicTags.TopicTagKey;
import com.brain1.masterdata.records.PostTransport;
import com.brain1.masterdata.repos.TopicTagsRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Merges updates to object, used for for patch rest for example
 */
@Service
public class TopicService {
    @Autowired
    TopicTagsRepo topicTagsRepo;

    public void decreatePostCountForTagInThisTopic(Long topicId, List<Tag> tags) {
        topicTagsRepo.decrementActivePostsForThisTopicTags(topicId,
                tags.stream().map(Tag::getId).collect(Collectors.toList()));
    }

    public void addTopicTag(final long topicId, final Post post, Tag tag) {

        System.out.format("adding topic tags", tag, topicId, "%n");
        topicTagsRepo.findByTag_IdAndTopic_Id(tag.getId(), topicId).map(topicTag -> {
            System.out.format("found adding count to " + topicTag.getCurrentPosts() + 1);
            topicTag.setCurrentPosts(topicTag.getCurrentPosts() + 1);
            return topicTagsRepo.save(topicTag);
        }).orElseGet(() -> {
            System.out.format("not found, adding new one " + tag + " " + post.getTopic()+ " " + post.getTopic().getId()+ " " + tag.getId());
            final TopicTags topicTag = new TopicTags();
            topicTag.setTag(tag);
            topicTag.setTopic(post.getTopic());
            topicTag.setCurrentPosts(topicTag.getCurrentPosts() + 1);
            topicTag.setId(TopicTagKey.newTopicTagKey(post.getTopic().getId(), tag.getId()));
            return topicTagsRepo.save(topicTag);
        });
    }
}