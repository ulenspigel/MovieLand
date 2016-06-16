package com.dkovalov.movieland.service;

import com.dkovalov.movieland.dto.UserCredentials;
import com.dkovalov.movieland.entity.UserToken;

public interface SecurityService {
    UserToken authenticateUser(UserCredentials credentials);
    void purgeExpiredTokens();
    boolean checkTokenValidity(int token);
    int getTokenUserId(int token);
    boolean isUsersToken(int token, int userId);
}
