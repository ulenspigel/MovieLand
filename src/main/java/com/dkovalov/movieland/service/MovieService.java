package com.dkovalov.movieland.service;

import com.dkovalov.movieland.dao.MovieDao;
import com.dkovalov.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.List;

@Service
public class MovieService implements DataLoader {
    private static final String RATING_TITLE = "rating:";
    private static final String PRICE_TITLE = "price:";
    @Autowired
    private MovieDao movieDao;
    @Autowired
    private GenreService genreService;

    @Override
    public void loadFromFile(BufferedReader reader) throws IOException {
        String entry = "";
        while ((entry = reader.readLine()) != null) {
            Movie movie = new Movie();
            movie.setTitle(entry.split("/")[0]);
            movie.setOriginalTitle(entry.split("/")[1]);
            movie.setYear(Integer.parseInt(reader.readLine()));
            movie.setCountry(reader.readLine());
            movie.setGenres(genreService.getGenresByNames(reader.readLine().split(",")));
            movie.setDescription(reader.readLine());
            DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols();
            decimalSymbols.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("###.#", decimalSymbols);
            decimalFormat.setParseBigDecimal(true);
            try {
                movie.setRating((BigDecimal) decimalFormat.parse(reader.readLine().replace(RATING_TITLE, "")));
                movie.setPrice((BigDecimal) decimalFormat.parse(reader.readLine().replace(PRICE_TITLE, "")));
            } catch (ParseException pe) {
                throw new RuntimeException(pe);
            }
            reader.readLine();
            movieDao.addMovie(movie);
        }
    }

    public Movie findByName(String name) {
        return movieDao.getByName(name);
    }

    public List<Movie> getAll() {
        return movieDao.getAll();
    }
}
