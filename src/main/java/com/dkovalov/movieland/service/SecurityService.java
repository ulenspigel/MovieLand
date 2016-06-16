package com.dkovalov.movieland.service;

import com.dkovalov.movieland.dto.UserCredentials;
import com.dkovalov.movieland.entity.UserToken;

public interface SecurityService {
    UserToken authenticateUser(UserCredentials credentials);
    void checkTokenValidity(int token);
    void purgeExpiredTokens();
}
