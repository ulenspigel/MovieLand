package com.dkovalov.movieland.dao;

public interface UserDao {
    int getUserIdByCredentials(String login, String password);
}
