package com.dkovalov.movieland.dao;

import com.dkovalov.movieland.entity.User;

public interface UserDao {
    User getUserByCredentials(String login, String password);
}
