package com.dkovalov.movieland.service.impl;

import com.dkovalov.movieland.controller.error.IncorrectCredentials;
import com.dkovalov.movieland.dao.UserDao;
import com.dkovalov.movieland.dto.UserCredentials;
import com.dkovalov.movieland.entity.UserToken;
import com.dkovalov.movieland.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.HashMap;

@Service
public class SecurityServiceImpl implements SecurityService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private HashMap<Integer, UserToken> tokens = new HashMap<>();

    @Autowired
    UserDao userDao;

    @Override
    public UserToken authenticateUser(UserCredentials credentials) {
        UserToken token;
        try {
            int userId = userDao.getUserIdByCredentials(credentials.getLogin(), credentials.getPassword());
            token = new UserToken(userId);
            storeToken(token);
        } catch (EmptyResultDataAccessException e) {
            throw new IncorrectCredentials();
        }
        return token;
    }

    private synchronized void storeToken(UserToken token) {
        tokens.put(token.hashCode(), token);
    }

    @Override
    public void checkTokenValidity() {
    }
}
