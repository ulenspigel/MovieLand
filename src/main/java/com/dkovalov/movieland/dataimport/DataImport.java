package com.dkovalov.movieland.dataimport;

import com.dkovalov.movieland.dao.postgres.UserDaoImpl;
import com.dkovalov.movieland.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataImport {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new RuntimeException("Two parameters have to be passed: path to file with data and entity name");
        }


        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        String dataEntry = "";
        while ((dataEntry = reader.readLine()) != null) {
            User user = new User();
            user.setDisplayName(dataEntry);
            user.setEmail(reader.readLine());
            user.setLogin(reader.readLine());
            reader.readLine();
            userDao.addUser(user);
        }
    }
}
