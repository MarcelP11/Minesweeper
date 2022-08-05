package service;

import entity.Rating;
import entity.Score;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

//prikaz na vytvorenie tabulky
/*
CREATE TABLE rating(
    game VARCHAR(64) NOT NULL,
    userName VARCHAR(64) NOT NULL,
    rating INT NOT NULL CHECK (rating>0 AND rating <=5),
    rated_on TIMESTAMP NOT NULL,
    UNIQUE (game, userName)
	);

*/

public class RatingServiceJDBC implements RatingService {
    private static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "postgres";
    private static final String STATEMENT_GET_AVERAGE = "SELECT TRUNC(AVG(rating),0) AS average FROM rating";
    private static final String STATEMENT_RESET = "DELETE FROM rating";
    private static final String STATEMENT_GET_RATE = "SELECT game,userName,rating FROM rating WHERE game = ? AND userName = ?";
    private static final String STATEMENT_SET_RATING = "INSERT INTO rating VALUES (?,?,?,?)";
    private static final String STATEMENT_UPDATE_RATING = "UPDATE rating SET rating=? WHERE userName=? AND game=?";


    @Override
    public void setRating(Rating rating) {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(STATEMENT_SET_RATING)) {
            statement.setString(1, rating.getGame());
            statement.setString(2, rating.getUsername());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException();
        }
    }

    @Override
    public int getAverageRating(String name) {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.createStatement()) {
            try (var rs = statement.executeQuery(STATEMENT_GET_AVERAGE)) {
                int avgrating = 0;
                if (rs.next()) {
                    avgrating = rs.getInt(1);
                }
                return avgrating;
            }
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }

    }

    @Override
    public int getRating(String name, String username) {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(STATEMENT_GET_RATE)) {
            statement.setString(1, name);
            statement.setString(2, username);
            try (var rs = statement.executeQuery()) {
                int rating = 0;
                if (rs.next()) {
                    rating = rs.getInt(3);
                }
                return rating;
            }
        }
     catch(SQLException e){
        throw new GameStudioException(e);
    }
}


    @Override
    public void reset() {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.createStatement()) {
            statement.executeUpdate(STATEMENT_RESET);
        } catch (SQLException e) {
            throw new GameStudioException();
        }
    }
}
