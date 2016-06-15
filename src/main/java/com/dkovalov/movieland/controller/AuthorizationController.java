package com.dkovalov.movieland.controller;

import com.dkovalov.movieland.deserializer.SecurityRequestDeserializer;
import com.dkovalov.movieland.dto.UserCredentials;
import com.dkovalov.movieland.entity.UserToken;
import com.dkovalov.movieland.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/v1/")
public class AuthorizationController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityService securityService;

    @Autowired
    private SecurityRequestDeserializer deserializer;

    @RequestMapping(value = "authorize", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public UserToken authorize(@RequestBody String json) {
        log.info("Authorization request received");
        long startTime = System.currentTimeMillis();
        UserToken token = securityService.authenticateUser(deserializer.authorizationRequest(json));
        log.info("Authorization complete. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
        return token;
    }
}
