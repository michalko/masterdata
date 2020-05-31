package com.brain1.masterdata.rests;

import javax.annotation.Nonnull;

import com.brain1.masterdata.records.StartTestSessionData;
import com.brain1.masterdata.repos.TopicRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/initSession")
@CrossOrigin(origins = { "http://localhost:3000", "https://brainmatter.xyz" })
public class InitSessionRest {
    @Autowired
    private WronglyAnsweredRest wronglyAnsweredRest;
    @Autowired
    TopicRepo topicRepo;

    @GetMapping("/start/{uid}/{topic}")
    public StartTestSessionData initSession(@PathVariable @Nonnull String uid, @PathVariable @Nonnull String topic) {
        System.out.println("yea "  +topic );
        return new StartTestSessionData(wronglyAnsweredRest.getAllForUser(uid, topic),
                topicRepo.findByName(topic).get().getCurrentPosts());
    }
}