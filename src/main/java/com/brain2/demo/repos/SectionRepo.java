package com.brain2.demo.repos;

import com.brain2.demo.models.Topicsections;
import com.brain2.demo.models.Topic;

import org.springframework.data.repository.CrudRepository;

public interface SectionRepo extends CrudRepository<Topicsections, Long> {


}