package com.brain2.demo.rests;

import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.brain2.demo.repos.UserRepo;
import com.brain2.demo.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = { "http://localhost:3000", "https://brainmatter.xyz" })
public class UserRest {
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService userService;

    @PatchMapping(value = "/{fid}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void patchUser(@NotNull @NotEmpty @RequestBody Map<String, @NotNull Object> updates,
            @NotNull @PathVariable(value = "fid") String fid) {
        var user = userRepo.findByFirebaseId(fid);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
        userService.partialUpdate(user.get(), updates);
    }

}