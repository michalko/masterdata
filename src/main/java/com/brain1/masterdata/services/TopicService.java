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

    public void addTopicTag(final PostTransport postTransport, final Post post, Tag tag) {
        topicTagsRepo.findByTag_IdAndTopic_Id(tag.getId(), postTransport.topicID()).map(topicTag -> {
            topicTag.setCurrentPosts(topicTag.getCurrentPosts() + 1);
            return topicTagsRepo.save(topicTag);
        }).orElseGet(() -> {
            final TopicTags topicTag = new TopicTags();
            topicTag.setTag(tag);
            topicTag.setTopic(post.getTopic());
            topicTag.setCurrentPosts(topicTag.getCurrentPosts() + 1);
            topicTag.setId(TopicTagKey.newTopicTagKey(post.getTopic().getId(), tag.getId()));
            return topicTagsRepo.save(topicTag);
        });
    }
}