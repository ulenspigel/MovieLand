package com.dkovalov.movieland.dao;

import com.dkovalov.movieland.entity.User;

public interface UserDao {
    void addUser(User user);
    User getByName(String name);
}
