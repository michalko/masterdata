package com.brain1.masterdata.repos;

import java.util.Optional;

import com.brain1.masterdata.models.Tag;

import org.springframework.data.repository.CrudRepository;

public interface TagRepo extends CrudRepository<Tag, Long> {

    Optional<Tag> findByName(String name);
    Iterable<Tag> findAllByNameIn(Iterable<String> names);
}