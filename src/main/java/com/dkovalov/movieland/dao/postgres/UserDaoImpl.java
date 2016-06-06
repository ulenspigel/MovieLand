package com.dkovalov.movieland.dao.postgres;

import com.dkovalov.movieland.dao.UserDao;
import com.dkovalov.movieland.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //TODO: inject value
    //TODO: binded variables
    //TODO: consider using batch operation
    private static final String INSERT_USER_SQL = "insert into ml.t_user(display_name, email, login) values (?, ?, ?)";

    @Override
    public void addUser(User user) {
        jdbcTemplate.update(INSERT_USER_SQL, user.getDisplayName(), user.getEmail(), user.getLogin());
    }
}
