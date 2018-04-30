package com.spring.springtoangular;


import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;

@RestController
public class RestApi {
    @RequestMapping(method = RequestMethod.GET, path = "/movies")
    public Movie[] getMovies() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/movies");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from movies;");
        ArrayList<Movie> movies = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getLong(0);
            String title = resultSet.getString(1);
            String rating = resultSet.getString(2);
            double criticRating = resultSet.getDouble(3);
            double ticketPrice = resultSet.getDouble(4);
            long directorId = resultSet.getLong(5);
            Movie movie = new Movie(id, title, rating, criticRating, ticketPrice, directorId);
            movies.add(movie);
        }

        Movie[] moviesArray = new Movie[movies.size()];
        movies.toArray(moviesArray);
        resultSet.close();
        statement.close();
        connection.close();
        return moviesArray;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/movies")
    public void addMovie(@RequestBody Movie movie) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/movies");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        statement.executeUpdate("insert into movies (title, rating, criticrating, ticketprice, director_id)"
            + "values (" + movie.getTitle() + ", " + movie.getRating() + ", " + movie.getCriticRating() + ", " + movie.getTicketPrice() + ", " + movie.getDirectorId() + ");");
        statement.close();
        connection.commit();
        connection.close();
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/movies/{id}")
    public Movie getMovie(@PathVariable long id) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/movies");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from movies where id = " + id + ";");
        Movie movie = null;
        while (resultSet.next()) {
            String title = resultSet.getString(1);
            String rating = resultSet.getString(2);
            double criticRating = resultSet.getDouble(3);
            double ticketPrice = resultSet.getDouble(4);
            long directorId = resultSet.getLong(5);
            movie = new Movie(id, title, rating, criticRating, ticketPrice, directorId);
        }
        resultSet.close();
        statement.close();
        connection.close();

        return movie;
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/movies/{id}")
    public void deleteMovie(@PathVariable long id) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/movies");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        statement.executeUpdate("delete movies where id = " + id + ";");

        statement.close();
        connection.commit();
        connection.close();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/actors")
    public Actor[] getActors() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/movies");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from actors;");
        ArrayList<Actor> movies = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getLong(0);
            String firstName = resultSet.getString(1);
            String lastName = resultSet.getString(2);
            Actor movie = new Actor(id, firstName, lastName);
            movies.add(movie);
        }

        Actor[] moviesArray = new Actor[movies.size()];
        movies.toArray(moviesArray);
        resultSet.close();
        statement.close();
        connection.close();
        return moviesArray;
    }
}
