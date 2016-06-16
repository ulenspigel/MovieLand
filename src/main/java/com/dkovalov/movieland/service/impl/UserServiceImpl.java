package com.dkovalov.movieland.service.impl;

import com.dkovalov.movieland.dao.UserDao;
import com.dkovalov.movieland.dto.UserCredentials;
import com.dkovalov.movieland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public int getUserIdByCredentials(UserCredentials credentials) {
        return userDao.getUserIdByCredentials(credentials.getLogin(), credentials.getPassword());
    }
}