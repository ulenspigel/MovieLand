package com.dkovalov.movieland.dao.mapper;

import com.dkovalov.movieland.entity.User;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        //TODO: implement mapping itself
        User user = new User();
        return user;
    }
}
