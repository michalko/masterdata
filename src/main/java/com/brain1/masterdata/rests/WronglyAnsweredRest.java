package com.brain1.masterdata.rests;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.brain1.masterdata.models.WronglyAnswered;
import com.brain1.masterdata.records.WronglyAnsweredRecord;
import com.brain1.masterdata.repos.PostRepo;
import com.brain1.masterdata.repos.TopicRepo;
import com.brain1.masterdata.repos.UserRepo;
import com.brain1.masterdata.repos.WronglyAnsweredRepo;
import com.brain1.masterdata.services.WronglyAnsweredService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/wronglyAnswered")
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

    @GetMapping("/{userId}")
    public Iterable<WronglyAnswered> getAllUsers(@PathVariable @Nonnull String userId) {
        System.out.println(userId);
        return wronglyAnsweredRepo.findAllByUserFirebaseId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addNew(@NotNull @NotEmpty @RequestBody final WronglyAnsweredRecord wronglyAnsweredRecord) {
        final var user = userRepo.findByFirebaseId(wronglyAnsweredRecord.uid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
        final var post = postRepo.findById(wronglyAnsweredRecord.pid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post not found"));
        final var topic = post.getTopic();

        final WronglyAnswered wa = new WronglyAnswered();
        wa.setPost(post);
        wa.setTopic(topic);
        wa.setUser(user);
        wa.setAnsweredWronglyTimes(1l);
        wronglyAnsweredRepo.save(wa);
    }

    @PatchMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void incDecTimesWrong(@NotNull @PathVariable(value = "uid") final String uid,
            @NotNull @PathVariable(value = "pid") final String pid,
            @NotNull @NotEmpty @RequestBody final Map<String, Object> updates) {
        final boolean inc = (boolean) updates.get("inc");
        final var wa = wronglyAnsweredRepo.findAllByUserFirebaseIdAndPostId(uid, pid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post not found"));
        wronglyAnsweredService.incOrDecWronglyTimesAnswered(wa, inc);
    }
}