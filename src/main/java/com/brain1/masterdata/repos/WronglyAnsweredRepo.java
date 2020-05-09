package com.brain1.masterdata.repos;

import java.util.Optional;

import javax.annotation.Nonnull;

import com.brain1.masterdata.models.WronglyAnswered;

import org.springframework.data.repository.CrudRepository;

public interface WronglyAnsweredRepo extends CrudRepository<WronglyAnswered, Long> {
    Iterable<WronglyAnswered> findAllByUserFirebaseId(String name);
    Optional<WronglyAnswered> findAllByUserFirebaseIdAndPostId(@Nonnull String uid, @Nonnull String pid);
}