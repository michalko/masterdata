package com.brain1.masterdata.services;

import com.brain1.masterdata.models.WronglyAnswered;
import com.brain1.masterdata.repos.WronglyAnsweredRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WronglyAnsweredService {
    @Autowired
    WronglyAnsweredRepo wronglyAnsweredRepo;

    public void incOrDecWronglyTimesAnswered(final WronglyAnswered updated, final boolean shouldIncrement) {
        if (shouldIncrement) {
            increment(updated);
        } else {
            decrement(updated);
        }
    }

    private void increment(final WronglyAnswered updated) {
        updated.setAnsweredWronglyTimes(updated.getAnsweredWronglyTimes() + 1);
        wronglyAnsweredRepo.save(updated);
    }

    private void decrement(final WronglyAnswered updated) {
        final var newAw = updated.getAnsweredWronglyTimes() - 1;
        if (newAw <= 0) {
            wronglyAnsweredRepo.delete(updated);
        } else {
            updated.setAnsweredWronglyTimes(newAw);
            wronglyAnsweredRepo.save(updated);
        }
    }
}