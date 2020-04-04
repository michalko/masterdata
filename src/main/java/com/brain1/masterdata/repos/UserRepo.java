package com.brain1.masterdata.repos;

import java.util.Optional;

import javax.transaction.Transactional;

import com.brain1.masterdata.models.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {

    Optional<User> findByFirebaseId(String fid);

    @Transactional
    @Modifying
    @Query("update User u set u.startedTest = ?1 where u.id = ?2")
    void setUserStartedTest(boolean startedTest, Integer id);
}