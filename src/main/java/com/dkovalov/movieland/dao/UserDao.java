package com.dkovalov.movieland.dao;

public interface UserDao {
    boolean isValidCredentials(String login, String password);
}
