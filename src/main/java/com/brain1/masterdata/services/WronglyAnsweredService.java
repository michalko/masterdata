package com.brain1.masterdata.services;

import javax.annotation.Nonnull;

import com.brain1.masterdata.models.WronglyAnswered;
import com.brain1.masterdata.records.WronglyAnsweredRecord;
import com.brain1.masterdata.repos.PostRepo;
import com.brain1.masterdata.repos.TopicRepo;
import com.brain1.masterdata.repos.UserRepo;
import com.brain1.masterdata.repos.WronglyAnsweredRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WronglyAnsweredService {
    @Autowired
    WronglyAnsweredRepo wronglyAnsweredRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    PostRepo postRepo;
    @Autowired
    TopicRepo topicRepo;

    public WronglyAnswered addNew(@Nonnull final WronglyAnsweredRecord wronglyAnsweredRecord) {
        System.out.println(wronglyAnsweredRecord);
        final var user = userRepo.findByFirebaseId(wronglyAnsweredRecord.uid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
        final var post = postRepo.findById(wronglyAnsweredRecord.pid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post not found"));
        final var topic = post.getTopic();

        final WronglyAnswered wa = new WronglyAnswered();
        wa.setPost(post);
        wa.setTopic(topic);
        wa.setUser(user);
        System.out.println(wa);
        return wa;
    }

    

}