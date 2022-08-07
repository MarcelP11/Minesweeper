package test;

import entity.Rating;
import org.junit.jupiter.api.Test;
import service.RatingService;
import service.RatingServiceJDBC;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class RatingServiceTest {
    private RatingService ratingService = new RatingServiceJDBC();

    @Test
    public void testAverageRate() {
        ratingService.reset();
        var date = new Date();
        ratingService.setRating(new Rating("Minesweeper", "Jan", 5, date));
        ratingService.setRating(new Rating("Stones", "Petra", 1, date));
        ratingService.setRating(new Rating("Minesweeper", "Dusan", 1, date));
        ratingService.setRating(new Rating("Minesweeper", "Jozef", 4, date));
        assertEquals(3, ratingService.getAverageRating("Minesweeper"));
    }

    @Test
    public void testAverageRateEmpty() {
        ratingService.reset();
        assertEquals(0, ratingService.getAverageRating("Minesweeper"));
    }

    @Test
    public void testGetRating() {
        ratingService.reset();
        ratingService.setRating(new Rating("Minesweeper", "Jan", 5, new Date()));
        ratingService.setRating(new Rating("Minesweeper", "Peter", 3, new Date()));
        ratingService.setRating(new Rating("Minesweeper", "Jozef", 1, new Date()));
        assertEquals(3, ratingService.getRating("Minesweeper", "Peter"));
    }

    @Test
    public void testGetRatingEmpty(){
        ratingService.reset();
        assertEquals(0,ratingService.getRating("Minesweeper","Peter"));
    }

    @Test
    public void testDuplicity(){
        ratingService.reset();
        ratingService.setRating(new Rating("Minesweeper", "Jan", 5, new Date()));
        ratingService.setRating(new Rating("Minesweeper", "Jan", 3, new Date()));
        ratingService.setRating(new Rating("Tiles", "Jan", 4, new Date()));
        assertEquals(3,ratingService.getRating("Minesweeper", "Jan"));
    }
}
