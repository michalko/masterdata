package com.brain2.demo.rests;

import java.util.List;

import javax.validation.constraints.Email;

import com.brain2.demo.Integration.IntegrationGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@CrossOrigin(origins = { "http://localhost:3000", "https://brainmatter.xyz" })
public class MailRest {

    @Autowired
    private IntegrationGateway integrationGateway;

    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    public void sentLinksMail(@RequestBody MailRequest mailRequest) {
        System.out.println(mailRequest.toString());
        integrationGateway.sendMessage(mailRequest.email);
    }
}

class MailRequest {
    @Email
    public String email;
    public List<String> listOfLinks;

    public MailRequest(@Email String email, List<String> listOfLinks) {
        this.email = email;
        this.listOfLinks = listOfLinks;
    }
}