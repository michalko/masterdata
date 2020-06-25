package com.brain1.masterdata.services;

import javax.annotation.Nonnull;

import com.brain1.masterdata.models.WronglyAnswered;
import com.brain1.masterdata.repos.WronglyAnsweredRepo;

import org.springframework.beans.factory.annotation.Autowired;

public class UpdateWronglyAnswersForUser {

    final static short wronglyLimit = 3;

    @Autowired
    WronglyAnsweredRepo wronglyAnsweredRepo;

    public void incOrDecWronglyTimesAnswered(@Nonnull final WronglyAnswered updated, final boolean shouldIncrement) {
        if (shouldIncrement)
            increment(updated);
        else
            decrement(updated);
    }

    private void increment(@Nonnull final WronglyAnswered updated) {
        if (updated.getAnsweredWronglyTimes() < wronglyLimit) {
            updated.setAnsweredWronglyTimes(updated.getAnsweredWronglyTimes() + 1);
            wronglyAnsweredRepo.save(updated);
        }
    }

    private void decrement(@Nonnull final WronglyAnswered updated) {
        final var newAw = updated.getAnsweredWronglyTimes() - 1;
        if (newAw <= 0) {
            wronglyAnsweredRepo.delete(updated);
        } else {
            updated.setAnsweredWronglyTimes(newAw);
            wronglyAnsweredRepo.save(updated);
        }
    }
}