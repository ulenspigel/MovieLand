package com.dkovalov.movieland.service.impl;

import com.dkovalov.movieland.controller.error.IncorrectCredentials;
import com.dkovalov.movieland.controller.error.InvalidToken;
import com.dkovalov.movieland.dao.UserDao;
import com.dkovalov.movieland.dto.UserCredentials;
import com.dkovalov.movieland.entity.UserToken;
import com.dkovalov.movieland.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class SecurityServiceImpl implements SecurityService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private HashMap<Integer, UserToken> tokens = new HashMap<>();

    @Autowired
    private UserDao userDao;

    @Value("${token.lifeTime.hours}")
    private int tokenLifetime;

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
    public synchronized void checkTokenValidity(int token) {
        if (tokens.containsKey(token)) {
            UserToken userToken = tokens.get(token);
            if (isTokenExpired(userToken.getGenerationTime())) {
                tokens.remove(token);
                throw new InvalidToken();
            }
        } else {
            throw new InvalidToken();
        }
    }

    @Override
    @Scheduled(fixedRate = 30_000_000)
    public synchronized void purgeExpiredTokens() {
        log.info("Start purging expired tokens");
        long startTime = System.currentTimeMillis();
        int purgedItems = 0;
        for (int token : tokens.keySet()) {
            if (isTokenExpired(tokens.get(token).getGenerationTime())) {
                tokens.remove(token);
                purgedItems++;
            }
        }
        log.info("Purging completed. {} items have been purged. Elapsed time - {} ms", purgedItems,
                System.currentTimeMillis() - startTime);
    }

    private boolean isTokenExpired(LocalDateTime generationTime) {
        return LocalDateTime.now().isAfter(generationTime.plusHours(tokenLifetime));
    }
}
