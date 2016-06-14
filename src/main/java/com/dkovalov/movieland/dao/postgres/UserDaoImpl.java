package com.dkovalov.movieland.dao.postgres;

import com.dkovalov.movieland.dao.UserDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    @Value("${sql.user.checkCredentials}")
    private String checkCredentialsSQL;

    @Override
    public boolean isValidCredentials(String login, String password) {
        return false;
    }
}
