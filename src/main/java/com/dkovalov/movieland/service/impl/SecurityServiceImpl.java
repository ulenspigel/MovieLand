package com.dkovalov.movieland.service.impl;

import com.dkovalov.movieland.controller.error.IncorrectCredentials;
import com.dkovalov.movieland.controller.error.InvalidToken;
import com.dkovalov.movieland.dto.UserCredentials;
import com.dkovalov.movieland.entity.UserToken;
import com.dkovalov.movieland.service.SecurityService;
import com.dkovalov.movieland.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SecurityServiceImpl implements SecurityService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private Map<Integer, UserToken> tokens = new ConcurrentHashMap<>();

    @Autowired
    private UserService userService;

    @Value("${token.lifeTime.hours}")
    private int tokenLifetime;

    @Override
    public UserToken authenticateUser(UserCredentials credentials) {
        UserToken token;
        try {
            token = new UserToken(userService.getUserIdByCredentials(credentials));
            tokens.put(token.getToken(), token);
        } catch (EmptyResultDataAccessException e) {
            log.error("User with given credentials was not found: {}", e);
            throw new IncorrectCredentials(e);
        }
        return token;
    }

    private UserToken findTokenValidate(int token) {
        if (tokens.containsKey(token)) {
            UserToken userToken = tokens.get(token);
            if (isTokenExpired(userToken.getGenerationTime())) {
                tokens.remove(token);
                log.error("Token {} has expired", token);
                throw new InvalidToken();
            }
            return userToken;
        } else {
            log.error("Token {} was not found", token);
            throw new InvalidToken();
        }
    }

    @Override
    public boolean checkTokenValidity(int token) {
        return (findTokenValidate(token) != null);
    }

    @Override
    public int getTokenUserId(int token) {
        return findTokenValidate(token).getUserId();
    }

    @Override
    public boolean isUsersToken(int token, int userId) {
        return (findTokenValidate(token).getUserId() == userId);
    }

    @Override
    @Scheduled(fixedRate = 30_000_000)
    // housekeeping job that launches every 30 minutes and purges expired tokens
    public void purgeExpiredTokens() {
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
