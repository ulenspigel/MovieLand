package com.dkovalov.movieland.service;

import com.dkovalov.movieland.dto.UserCredentials;
import com.dkovalov.movieland.entity.User;

public interface UserService {
    User getUserByCredentials(UserCredentials credentials);
}
