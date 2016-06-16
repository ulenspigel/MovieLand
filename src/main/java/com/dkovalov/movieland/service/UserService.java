package com.dkovalov.movieland.service;

import com.dkovalov.movieland.dto.UserCredentials;

public interface UserService {
    int getUserIdByCredentials(UserCredentials credentials);
}
