package com.dkovalov.movieland.service.impl;

import com.dkovalov.movieland.controller.error.IncorrectCredentials;
import com.dkovalov.movieland.controller.error.InvalidToken;
import com.dkovalov.movieland.dto.UserCredentials;
import com.dkovalov.movieland.entity.User;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SecurityServiceImpl implements SecurityService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private Map<Integer, UserToken> tokens = new ConcurrentHashMap<>();

    @Autowired
    private UserService userService;

    @Value("${token.housekeeping.lifeTimeHours:2}")
    private int tokenLifetime;

    @Override
    public UserToken authenticateUser(UserCredentials credentials) {
        UserToken token;
        try {
            User user = userService.getUserByCredentials(credentials);
            token = new UserToken(user.getUserId(), user.isAdmin(), tokenLifetime * 60 * 60);
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
            if (userToken.isExpired()) {
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
        return findTokenValidate(token) != null;
    }

    @Override
    public int getTokenUserId(int token) {
        return findTokenValidate(token).getUserId();
    }

    @Override
    public boolean isUsersToken(int token, int userId) {
        return findTokenValidate(token).getUserId() == userId;
    }

    @Override
    public boolean checkTokenAdminRights(int token) {
        return findTokenValidate(token).isAdmin();
    }

    @Override
    @Scheduled(fixedRateString = "${token.housekeeping.launchInterval:1800000}")
    // housekeeping job that launches every 30 minutes and purges expired tokens
    public void purgeExpiredTokens() {
        log.info("Start purging expired tokens from the set of {} tokens", tokens.size());
        long startTime = System.currentTimeMillis();
        Set<Integer> expiredTokens = new HashSet<>();
        tokens.forEach((k, v) -> {
            if (v.isExpired()) {
                expiredTokens.add(k);
            }
        });
        log.debug("{} tokens have been expired", expiredTokens.size());
        tokens.keySet().removeAll(expiredTokens);
        log.info("Purging completed. {} tokens are currently valid. Elapsed time - {} ms", tokens.size(),
                System.currentTimeMillis() - startTime);
    }
}
