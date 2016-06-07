package com.dkovalov.movieland.service;

import com.dkovalov.movieland.dao.UserDao;
import com.dkovalov.movieland.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;

public class UserService implements DataLoader {
    //TODO: move to XML
    @Autowired
    private UserDao userDao;

    @Override
    public void loadFromFile(BufferedReader reader) throws IOException {
        String entry = "";
        while ((entry = reader.readLine()) != null) {
            User user = new User();
            user.setDisplayName(entry);
            user.setEmail(reader.readLine());
            user.setPassword(reader.readLine());
            reader.readLine();
            userDao.addUser(user);
        }
    }

    public User findByName(String name) {
        return userDao.getByName(name);
    }
}
