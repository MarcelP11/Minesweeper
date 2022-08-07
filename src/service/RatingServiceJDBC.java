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
    private static final String STATEMENT_GET_AVERAGE = "SELECT TRUNC(AVG(rating),0) AS average FROM rating WHERE game = ?";
    private static final String STATEMENT_RESET = "DELETE FROM rating";
    private static final String STATEMENT_GET_RATE = "SELECT game,userName,rating FROM rating WHERE game = ? AND userName = ?";
    private static final String STATEMENT_SET_RATING = "INSERT INTO rating VALUES (?,?,?,?)";
    private static final String STATEMENT_UPDATE_RATING = "UPDATE rating SET rating=? WHERE userName=? AND game=?";

    private static final String STATEMENT_UPDATE_OR_SET_RATING = "INSERT INTO rating VALUES (?,?,?,?) ON CONFLICT ON CONSTRAINT rating_game_username_key DO UPDATE SET rating=EXCLUDED.rating";


    @Override
    public void setRating(Rating rating) {
        //funkcne s SQL query ON CONFLICT ON CONSTRAINT
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(STATEMENT_UPDATE_OR_SET_RATING)) {
            statement.setString(1, rating.getGame());
            statement.setString(2, rating.getUsername());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException();
        }

        /*
        //skuska 1 s podmienkou pre username

        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(STATEMENT_GET_RATE)) {
            statement.setString(1, rating.getGame());
            statement.setString(2, rating.getUsername());
            try (var rs = statement.executeQuery()) {
                String userNameCompare="";
                if (rs.next()) {
                    userNameCompare = rs.getString(2);
                }
                if (userNameCompare == rating.getUsername()) {
                    try (var connection2 = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                            var statement2 = connection.prepareStatement(STATEMENT_UPDATE_RATING)) {
                        statement2.setInt(1, rating.getRating());
                        statement2.setString(2, rating.getUsername());
                        statement2.setString(3, rating.getGame());
                        statement2.executeUpdate();
                    }
                } else {
                    try (var connection3 = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                            var statement3 = connection.prepareStatement(STATEMENT_SET_RATING)) {
                        statement3.setString(1, rating.getGame());
                        statement3.setString(2, rating.getUsername());
                        statement3.setInt(3, rating.getRating());
                        statement3.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
                        statement3.executeUpdate();
                    }
                }
            }

        }catch (SQLException e) {
            throw new GameStudioException(e);
        }
*/


        /*
        //iba vkladanie bez overovania

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
        */
    }

    @Override
    public int getAverageRating(String name) {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD); //vytvorime spojenie
             var statement = connection.prepareStatement(STATEMENT_GET_AVERAGE)) { //pripravime si sql query
            statement.setString(1, name);  //doplnime namiesto ? parameter z funkcie
            try (var rs = statement.executeQuery()) {   //vykoname query
                int avgrating = 0;   //vytvorime premennu do ktorej budeme ukladat vysledok
                if (rs.next()) {
                    avgrating = rs.getInt(1);  //vyberieme zo stlpca 1 hodnotu a ulozime ju do premennej
                }
                return avgrating;   //vraciame hodnotu premennej
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
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }


    @Override
    public void reset() {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);  //vytvorime spojenie
             var statement = connection.createStatement()) {  //vytvorime statement
            statement.executeUpdate(STATEMENT_RESET);  //vykoname statement
        } catch (SQLException e) {
            throw new GameStudioException();
        }
    }
}
