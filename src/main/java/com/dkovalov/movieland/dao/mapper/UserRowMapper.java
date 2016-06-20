package com.dkovalov.movieland.dao.mapper;

import com.dkovalov.movieland.entity.User;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));
        user.setDisplayName(resultSet.getString("display_name"));
        user.setEmail(resultSet.getString("email"));
        user.setIsAdmin(resultSet.getString("is_admin").equals("y"));
        return user;
    }
}