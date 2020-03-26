package com.brain2.demo.rests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.brain2.demo.models.Post;
import com.brain2.demo.models.Tag;
import com.brain2.demo.models.TopicTags;
import com.brain2.demo.models.TopicTags.TopicTagKey;
import com.brain2.demo.records.PostTransport;
import com.brain2.demo.repos.PostRepo;
import com.brain2.demo.repos.TagRepo;
import com.brain2.demo.repos.TopicRepo;
import com.brain2.demo.repos.TopicTagsRepo;
import com.brain2.demo.services.MergePrimitiveUpdatesService;
import com.brain2.demo.services.TopicService;
import com.google.common.collect.Sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = { "http://localhost:3000", "https://brainmatter.xyz" })
public class PostRest {
    Random random = new Random();

    @Autowired
    TopicService topicService;
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

    @Resource(name = "lastReadPosts")
    com.brain2.demo.rests.PostRest.MyConfiguration.LastReadPosts lastReadPosts;

    @PostMapping
    public Post createPost(@NotNull @NotEmpty @RequestBody final PostTransport postTransport) {
        final var post = new Post();
        topicRepo.findById(postTransport.topicID()).map(topic -> {
            post.setTopic(topic);
            return post;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Topic not found"));

        if (!postTransport.tags().isEmpty()) {
            final List<String> tags = postTransport.tags();
            final List<Tag> tagsList = new ArrayList<Tag>();
            tags.forEach(tagName -> {
                var tag = tagRepo.findByName(tagName).orElseGet(() -> tagRepo.save(new Tag(tagName)));
                tagsList.add(tag);
                topicService.addTopicTag(postTransport, post, tag);
            });
            post.setTags(tagsList);
        }
        post.setId(postTransport.id());
        post.setRealPostsInTopics(postTransport.realPostsInTopics());

        return postRepo.save(post);
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void patchPost(@NotNull @NotEmpty @RequestBody final Map<String, Object> updates,
            @NotNull @PathVariable(value = "id") final String id) {
        final var post = postRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post not found"));

        if (updates.get("topic") != null) {
            final Long topicID = Long.valueOf((Integer) updates.get("topic"));
            if (post.getTopic().getId() != topicID) {
                topicRepo.findById(topicID).map(topic -> {
                    post.setTopic(topic);
                    return post;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Topic not found"));
            }
            updates.remove("topic");
        }

        if (updates.get("tags") != null) {
            final List<String> tags = (ArrayList) updates.get("tags");
            if (!post.getTags().stream().map(Tag::getName).collect(Collectors.toList()).equals(tags)) {
                final List<Tag> tagsList = new ArrayList<Tag>();
                tags.forEach(tagName -> {
                    tagsList.add(tagRepo.findByName(tagName).orElseGet(() -> tagRepo.save(new Tag(tagName))));
                });
                post.setTags(tagsList);
            }
            updates.remove("tags");
        }

        mergeUpdatesService.mergeUpdates(post, updates);
        postRepo.save(post);
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public void setPost(@NotNull @RequestBody final Map<String, Object> updates) {
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

    // @Cacheable(value = "postsNearRank")
    @GetMapping(value = "/getRandomBetween/{topic}")
    @ResponseStatus(code = HttpStatus.OK)
    public @NotNull Integer getRandomPostBetween(@NotNull @PathVariable(value = "topic") final String topic,
            @RequestParam("topRank") final int topRank) {

        final Set<String> combinedSet = Sets.union(lastReadPosts.getLastPostsIds(), Set.of(lastReadPosts.getLastPid()));
        final var list = postRepo.findRandomsWithinCorrectRatio(topic, topRank, combinedSet);

        final var listSize = list.size();
        if (lastReadPosts.getPostsNum() == 0 || Math.abs(lastReadPosts.getTopRank() - topRank) > 10) {
            lastReadPosts.setPostsNum(listSize);
            lastReadPosts.setTopRank(topRank);
        }

        final var gauss = nextPostIndex(listSize);
        final var postToReturn = list.get(gauss);
        lastReadPosts.addPost(postToReturn.getId());
        lastReadPosts.setLastPid(postToReturn.getId());

        return postToReturn.getRealPostsInTopics();
    }

    private int nextPostIndex(final int listSize) {
        final var gauss = (int) ((listSize / 4) * (random.nextGaussian()) + (listSize / 2));
        return gauss < 0 || gauss >= listSize ? listSize / 2 : gauss;
    }

    @Configuration
    public class MyConfiguration {

        @Bean
        @SessionScope
        public LastReadPosts lastReadPosts() {
            return new LastReadPosts();
        }

        @Component
        public class LastReadPosts {
            private final Set<String> lastPosts;
            private int postsNum;
            private int topRank;
            private String lastPid = "0";

            LastReadPosts() {
                lastPosts = Sets.newHashSet("1");
            }

            void clear() {
                lastPosts.clear();
                postsNum = 0;
            }

            Set<String> getLastPostsIds() {
                return lastPosts;
            }

            void addPost(final String pid) {
                lastPosts.add(pid);
                if (lastPosts.size() > (postsNum / 2)) {
                    clear();
                }
            }

            public int getPostsNum() {
                return postsNum;
            }

            public void setPostsNum(final int postsNum) {
                this.postsNum = postsNum;
            }

            public int getTopRank() {
                return topRank;
            }

            public void setTopRank(final int topRank) {
                this.topRank = topRank;
            }

            public String getLastPid() {
                return lastPid;
            }

            public void setLastPid(final String lastPid) {

                System.out.println("last posts ids 2: " + lastPid);
                this.lastPid = lastPid;
            }
        }

    }

}
