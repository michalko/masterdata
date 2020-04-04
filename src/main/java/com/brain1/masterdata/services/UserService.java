package com.brain1.masterdata.services;

import java.util.Map;

import com.brain1.masterdata.models.User;
import com.brain1.masterdata.repos.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
public class UserService {
    @Autowired
    MergePrimitiveUpdatesService mergeUpdatesService;
    @Autowired
    UserRepo userRepo;

    public void partialUpdate(User user, Map<String, Object> updates) {
        mergeUpdatesService.mergeUpdates(user, updates);
        userRepo.save(user);
    }
}