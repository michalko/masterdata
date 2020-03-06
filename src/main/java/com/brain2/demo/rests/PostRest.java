package com.brain2.demo.rests;

import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.brain2.demo.models.Post;
import com.brain2.demo.repos.PostRepo;
import com.brain2.demo.repos.TopicRepo;
import com.brain2.demo.services.MergeUpdatesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    PostRepo postRepo;

    @Autowired
    TopicRepo topicRepo;
    @Autowired
    MergeUpdatesService mergeUpdatesService;

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

    @GetMapping(value = "/getRandomBetween")
    @ResponseStatus(code = HttpStatus.OK)
    public @NotNull Integer getRandomPostBetween(@NotNull String topic, @NotNull Integer corectPrecent) {
        return postRepo.findRandomWithCorrectRatioBetween(topic, corectPrecent - 10, corectPrecent + 10);
    }
}