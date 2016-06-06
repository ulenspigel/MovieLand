package com.dkovalov.movieland.dao.postgres;

import com.dkovalov.movieland.dao.UserDao;
import com.dkovalov.movieland.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //TODO: replace with autowired
    private String insertUserSQL = "insert into ml.user(display_name, email, login) values (?, ?, ?)";

    public void addUser(User user) {
        jdbcTemplate.update(insertUserSQL, user.getDisplayName(), user.getEmail(), user.getLogin());
    }
}
