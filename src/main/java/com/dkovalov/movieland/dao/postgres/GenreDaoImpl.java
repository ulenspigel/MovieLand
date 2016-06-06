package com.dkovalov.movieland.dao.postgres;

import com.dkovalov.movieland.dao.GenreDao;
import com.dkovalov.movieland.dao.mapper.GenreRowMapper;
import com.dkovalov.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("genreDao")
public class GenreDaoImpl implements GenreDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    //TODO: inject value
    //TODO: binded variables
    //TODO: consider using batch operation
    private static final String INSERT_GENRE_SQL = "insert into ml.genre(genre_name) values (?)";
    private static final String LOOKUP_GENRE_SQL = "select * from ml.genre where genre_name = ?";

    @Override
    public void addGenre(Genre genre) {
        jdbcTemplate.update(INSERT_GENRE_SQL, genre.getName());
    }

    @Override
    public Genre getByName(String name) {
        return jdbcTemplate.queryForObject(LOOKUP_GENRE_SQL, new Object[]{name}, new GenreRowMapper());
    }
}
