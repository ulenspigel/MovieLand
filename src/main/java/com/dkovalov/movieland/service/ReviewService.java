package com.dkovalov.movieland.service;

import com.dkovalov.movieland.dao.ReviewDao;
import com.dkovalov.movieland.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;

public class ReviewService implements DataLoader {
    @Autowired
    private ReviewDao reviewDao;
    @Autowired
    private MovieService movieService;
    @Autowired
    private UserService userService;

    @Override
    public void loadFromFile(BufferedReader reader) throws IOException {
        String entry = "";
        while ((entry = reader.readLine()) != null) {
            Review review = new Review();
            review.setMovie(movieService.findByName(entry.trim()));
            review.setUser(userService.findByName(reader.readLine().trim()));
            review.setText(reader.readLine());
            reader.readLine();
            reviewDao.addReview(review);
        }
    }
}
