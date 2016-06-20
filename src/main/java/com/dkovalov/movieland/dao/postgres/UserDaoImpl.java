package com.dkovalov.movieland.dao.postgres;

import com.dkovalov.movieland.dao.UserDao;
import com.dkovalov.movieland.dao.mapper.UserRowMapper;
import com.dkovalov.movieland.entity.User;
import com.dkovalov.movieland.entity.UserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    private Logger log = LoggerFactory.getLogger(getClass());
    private static RowMapper<User> mapper = new UserRowMapper();

    @Value("${sql.user.byCredentials}")
    private String userByCredentialsSQL;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User getUserByCredentials(String login, String password) {
        log.debug("Getting user with e-mail {} by it's credentials", login);
        long startTime = System.currentTimeMillis();
        User user = jdbcTemplate.queryForObject(userByCredentialsSQL, new Object[] {login, password}, mapper);
        log.debug("User has been found. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
        return user;
    }
}
