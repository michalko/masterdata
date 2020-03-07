package com.brain2.demo.rests;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.brain2.demo.models.Post;
import com.brain2.demo.repos.PostRepo;
import com.brain2.demo.repos.TopicRepo;
import com.brain2.demo.rests.PostRest.MyConfiguration.LastReadPosts;
import com.brain2.demo.services.MergeUpdatesService;
import com.google.common.collect.Sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    PostRepo postRepo;

    @Autowired
    TopicRepo topicRepo;
    @Autowired
    MergeUpdatesService mergeUpdatesService;

    @Resource(name = "lastReadPosts")
    LastReadPosts lastReadPosts;

    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public void setPost(@NotNull @RequestBody final Map<String, @NotNull Object> updates) {

        System.out.println(updates);
        System.out.println(updates.get("topic"));

        final var post = new Post();
        post.setId(updates.get("id").toString());
        final var topic = topicRepo.findById(Long.valueOf(updates.get("topic").toString()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Topic not found"));
        post.setTopic(topic);
        updates.remove("topic");
        mergeUpdatesService.mergeUpdates(post, updates);

        System.out.println(updates);
        System.out.println(post);
        postRepo.save(post);
    }

    @PatchMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void patchPost(@NotNull @NotEmpty @RequestBody final Map<String, @NotNull Object> updates,
            @NotNull @PathVariable(value = "id") final Long id) {
        final var post = postRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post not found"));
        mergeUpdatesService.mergeUpdates(post, updates);
        postRepo.save(post);
    }

    @GetMapping(value = "/getRandomBetween/{topic}")
    @ResponseStatus(code = HttpStatus.OK)
    public @NotNull Integer getRandomPostBetween(@NotNull @PathVariable(value = "topic") final String topic,
            @RequestParam("topRank") final int topRank) {

        System.out.println("last posts ids 1: " + lastReadPosts.getLastPostsIds().toString());
        Set<String> combinedSet = Sets.union(lastReadPosts.getLastPostsIds(), Set.of(lastReadPosts.getLastPid()));
        final var list = postRepo.findRandomsWithinCorrectRatio(topic, topRank, combinedSet);
        final var listSize = list.size();
        if (lastReadPosts.getPostsNum() == 0 || Math.abs(lastReadPosts.getTopRank() - topRank) > 10) {
            lastReadPosts.setPostsNum(listSize);
            lastReadPosts.setTopRank(topRank);
        }

        final var g = random.nextGaussian();
        final var g1 = (listSize / 4) * (g);
        final var g2 = g1 + (listSize / 2);
        final var gauss = (int) (g2);
        System.out.println("list 1 " + list.toString());
        System.out.println("gauss " + gauss);
        System.out.println("list 2 " + list.size());
        final int gaussFinal = gauss < 0 || gauss >= listSize ? listSize / 2 : gauss;

        final var postToReturn = list.get(gaussFinal);
        lastReadPosts.addPost(postToReturn.getId());

        lastReadPosts.setLastPid(postToReturn.getId());
        System.out.println("last posts ids 2: " + lastReadPosts.getLastPostsIds().toString());
        return postToReturn.getRealPostsInTopics();
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

            public void setPostsNum(int postsNum) {
                this.postsNum = postsNum;
            }

            public int getTopRank() {
                return topRank;
            }

            public void setTopRank(int topRank) {
                this.topRank = topRank;
            }

            public String getLastPid() {
                return lastPid;
            }

            public void setLastPid(String lastPid) {
                this.lastPid = lastPid;
            }
        }

    }

}
