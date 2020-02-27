package com.brain2.demo.services;

import java.util.Map;

import com.brain2.demo.models.User;
import com.brain2.demo.repos.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
public class UserService {
    @Autowired
    MergeUpdates partialUpdateService;

    @Autowired
    UserRepo userRepo;

    public void partialUpdate(User user, Map<String, Object> updates) {

        partialUpdateService.partialUpdate(user, updates);

        System.out.println(user.toString());
        userRepo.save(user);
    }

    private void removeIDs(Map<String, Object> updates) {
        updates.remove("id");
        updates.remove("firebase_id");
    }
}