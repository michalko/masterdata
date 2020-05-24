package com.brain1.masterdata.rests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.brain1.masterdata.models.Post;
import com.brain1.masterdata.models.Tag;
import com.brain1.masterdata.records.PostTransport;
import com.brain1.masterdata.repos.PostRepo;
import com.brain1.masterdata.repos.TagRepo;
import com.brain1.masterdata.repos.TopicRepo;
import com.brain1.masterdata.repos.TopicTagsRepo;
import com.brain1.masterdata.services.MergePrimitiveUpdatesService;
import com.brain1.masterdata.services.PostService;
import com.brain1.masterdata.services.TopicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = { "http://localhost:3000", "https://brainmatter.xyz" })
public class PostRest {
    @Autowired
    TopicService topicService;
    @Autowired
    PostService postService;
    @Autowired
    MergePrimitiveUpdatesService mergeUpdatesService;
    @Autowired
    PostRepo postRepo;
    @Autowired
    TopicRepo topicRepo;
    @Autowired
    TagRepo tagRepo;
    @Autowired
    TopicTagsRepo topicTagsRepo;

    @DeleteMapping
    public void deletePost(@NotNull @NotEmpty @RequestBody final PostTransport postTransport) {
        final var post = postRepo.findById(postTransport.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post not found"));
        final var topic = post.getTopic();
        topicService.decreatePostCountForTagInThisTopic(topic.getId(), post.getTags());

        if (topic.getCurrentPosts() > 0) {
            // topicRepo.delete(topic);
            topic.setCurrentPosts(topic.getCurrentPosts() - 1);
            topicRepo.save(topic);
        }

        postRepo.delete(post);
    }

    @PostMapping
    public Post addNewPost(@NotNull @NotEmpty @RequestBody final PostTransport postTransport) {
        final var post = new Post();
        topicRepo.findById(postTransport.topicID()).map(topic -> {
            topic.setCurrentPosts(topic.getCurrentPosts() + 1);
            post.setTopic(topic);
            return post;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Topic not found"));

        System.out.println("tags" + postTransport.tags().toString());
        if (!postTransport.tags().isEmpty()) {
            final List<String> tags = postTransport.tags();
            postService.updateTagsInDb(post, postTransport.topicID(), tags);
        }
        post.setId(postTransport.id());
        post.setRealPostsInTopics(postTransport.realPostsInTopics());

        System.out.println("saving" + post);
        return postRepo.save(post);
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void patchPost(@NotNull @NotEmpty @RequestBody final Map<String, Object> updates,
            @NotNull @PathVariable(value = "id") final String id) {
        final var post = postRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post not found"));

        final Long topicID = Long.valueOf((Integer) updates.get("topic"));
        if (updates.get("topic") != null) {
            if (post.getTopic().getId() != topicID) {
                topicRepo.findById(topicID).map(topic -> {
                    post.setTopic(topic);
                    return post;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Topic not found"));
            }
            updates.remove("topic");
        }

        if (updates.get("tags") != null) {
            final List<String> tags = (List) updates.get("tags");
            if (!post.getTags().stream().map(Tag::getName).collect(Collectors.toList()).equals(tags)) {
                postService.updateTagsInDb(post, topicID, tags);
            }
            updates.remove("tags");
        }

        mergeUpdatesService.mergeUpdates(post, updates);
        postRepo.save(post);
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public void updatePost(@NotNull @RequestBody final Map<String, Object> updates) {
        System.out.println(updates);
        System.out.println(updates.get("topic"));

        if (updates.get("correctPrecent") != null && updates.get("correctPrecent") instanceof Integer correctPrecent) {
            updates.put("correctPrecent", (double) updates.get("correctPrecent"));
        }

        if (updates.get("tags") != null && ((List) updates.get("tags")).size() > 0) {
            updates.put("tags",
                    ((List) updates.get("tags")).stream().map(o -> new Tag((String) o)).collect(Collectors.toList()));
        }

        final var post = new Post();
        post.setId(updates.get("id").toString());
        final var topic = topicRepo.findById(Long.valueOf(updates.get("topic").toString()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Topic not found"));
        post.setTopic(topic);
        updates.remove("topic");
        mergeUpdatesService.mergeUpdates(post, updates);

        postRepo.save(post);
    }

}
