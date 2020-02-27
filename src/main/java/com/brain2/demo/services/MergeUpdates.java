package com.brain2.demo.services;

import java.util.Map;

import com.brain2.demo.models.User;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;


@Service
public class MergeUpdates {
    public <T> void partialUpdate(T object, Map<String, Object> updates) {
        removeIDs(updates);

        updates.forEach((k, v) -> {
            // use reflection to get field k on object and set it to value v
            // Change Claim.class to whatver your object is: Object.class
            var field = ReflectionUtils.findField(User.class, k); // find field in the object class
            field.setAccessible(true); 
            ReflectionUtils.setField(field, object, v); // set given field for defined object to value V
        });
        
        System.out.println(object.toString());
    }

    private void removeIDs(Map<String, Object> updates) {
        updates.remove("id");
        updates.remove("firebase_id");
    }
}