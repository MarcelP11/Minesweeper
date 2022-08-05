package service;

import entity.Rating;

public interface RatingService {
    void setRating(Rating rating);
    int getAverageRating(String name);
    int getRating(String name, String username);
    void reset();
}
