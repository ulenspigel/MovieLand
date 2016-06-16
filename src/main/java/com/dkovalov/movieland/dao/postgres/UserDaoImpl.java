package com.dkovalov.movieland.dao.postgres;

import com.dkovalov.movieland.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Value("${sql.user.byCredentials}")
    private String checkCredentialsSQL;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int getUserIdByCredentials(String login, String password) {
        log.debug("Getting user with e-mail {} byt it's credentials", login);
        long startTime = System.currentTimeMillis();
        int userId = jdbcTemplate.queryForObject(checkCredentialsSQL, new Object[] {login, password}, Integer.class);
        log.debug("User has been found. Elapsed time - {} ms", System.currentTimeMillis() - startTime);
        return userId;
    }
}
