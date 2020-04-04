package com.brain1.masterdata.repos;

import java.util.ArrayList;

import com.brain1.masterdata.models.Topicsections;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SectionRepo extends CrudRepository<Topicsections, Long> {

}