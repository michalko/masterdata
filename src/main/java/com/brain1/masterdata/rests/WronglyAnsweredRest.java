package com.brain1.masterdata.rests;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.brain1.masterdata.models.WronglyAnswered;
import com.brain1.masterdata.records.WronglyAnsweredRecord;
import com.brain1.masterdata.records.WronglyAnsweredRecordToCore;
import com.brain1.masterdata.repos.PostRepo;
import com.brain1.masterdata.repos.TopicRepo;
import com.brain1.masterdata.repos.UserRepo;
import com.brain1.masterdata.repos.WronglyAnsweredRepo;
import com.brain1.masterdata.services.UpdateWronglyAnswersForUser;
import com.brain1.masterdata.services.WronglyAnsweredService;
import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wrongly")
@CrossOrigin(origins = { "http://localhost:3000", "https://brainmatter.xyz" })
public class WronglyAnsweredRest {
    @Autowired
    WronglyAnsweredRepo wronglyAnsweredRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    PostRepo postRepo;
    @Autowired
    TopicRepo topicRepo;
    @Autowired
    WronglyAnsweredService wronglyAnsweredService;
    @Autowired
    UpdateWronglyAnswersForUser updateWronglyAnswersForUser;

    @GetMapping("/{uid}/{topic}")
    public List<WronglyAnsweredRecordToCore> getAllForUser(@PathVariable @Nonnull String uid,
            @PathVariable @Nonnull String topic) {
        System.out.println("get wrongly for user" + uid + " topic " + topic);
        return wronglyAnsweredRepo.findAllByUserFirebaseIdAndTopicName(uid, topic).stream()
                .map(wae -> new WronglyAnsweredRecordToCore(wae.getTopic().getName(),
                        wae.getPost().getRealPostsInTopics(), wae.getPost().getId()))
                .collect(Collectors.toList());
    }

    @GetMapping("/count/{uid}")
    public int countForUser(@PathVariable @Nonnull String uid) {
        return wronglyAnsweredRepo.findAllByUserFirebaseId(uid).size();
    }

    @PostMapping("/{uid}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void incDecTimesWrong(@PathVariable @Nonnull String uid,
            @NotNull @NotEmpty @RequestBody final List<WronglyAnsweredRecord> warsArr) {
        Lists.newArrayList(warsArr).forEach(war -> updateWronglyAnswersForUser
                .incOrDecWronglyTimesAnswered(getWronglyAnsweredEntity(war), war.inc()));
    }

    private WronglyAnswered getWronglyAnsweredEntity(@Nonnull final WronglyAnsweredRecord war) {
        return wronglyAnsweredRepo.findAllByUserFirebaseIdAndPostId(war.uid(), war.pid())
                .orElseGet(() -> wronglyAnsweredService.addNew(war));
    }
}