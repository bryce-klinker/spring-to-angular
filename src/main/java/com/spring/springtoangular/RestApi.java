package com.spring.springtoangular;


import com.sun.org.apache.regexp.internal.RE;
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

    @RequestMapping(method = RequestMethod.GET, path = "/movies/{id}")
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

    @RequestMapping(method = RequestMethod.GET, path = "/movies/{id}/actors")
    public Actor[] getMovieActors(@PathVariable long id) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/movies");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from movie_actors ma join actors a on ma.actor_id = a.id where movie_id = " + id + ";");
        ArrayList<Actor> movies = new ArrayList<>();
        while (resultSet.next()) {
            long actorId = resultSet.getLong(2);
            String firstName = resultSet.getString(3);
            String lastName = resultSet.getString(4);
            Actor movie = new Actor(actorId, firstName, lastName);
            movies.add(movie);
        }

        Actor[] moviesArray = new Actor[movies.size()];
        movies.toArray(moviesArray);
        resultSet.close();
        statement.close();
        connection.close();
        return moviesArray;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/movies/{id}/director")
    public Director getMovieDirector(@PathVariable long id) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/movies");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from movies m join directors d on m.director_id = d.id where m.id = " + id + ";");
        Director movie = null;
        while (resultSet.next()) {
            long directorId = resultSet.getLong(2);
            String firstName = resultSet.getString(3);
            String lastName = resultSet.getString(4);
            movie = new Director(directorId, firstName, lastName);
        }

        resultSet.close();
        statement.close();
        connection.close();
        return movie;
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

    @RequestMapping(method = RequestMethod.POST, path = "/movies/{movieId}/actors/{actorId}")
    public void addMovieActor(@PathVariable long movieId, @PathVariable long actorId) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/movies");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        statement.executeUpdate("insert into movie_actors (movie_id, actor_id)"
                + "values (" + movieId + ", " + actorId + ");");
        statement.close();
        connection.commit();
        connection.close();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/movies/{movieId}/director/{directorId}")
    public void addMovieDirector(@PathVariable long movieId, @PathVariable long directorId) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/movies");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        statement.executeUpdate("update movies set director_id = " + directorId + " where id = " + movieId + ";");
        statement.close();
        connection.commit();
        connection.close();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/movies/{id}")
    public void deleteMovie(@PathVariable long id) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/movies");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from movies where id = " + id + ";");

        statement.close();
        connection.commit();
        connection.close();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/movies/{movieId}/actors/{actorId}")
    public void deleteMovieActor(@PathVariable long movieId, @PathVariable long actorId) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/movies");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from movie_actors where movie_id = " + movieId + " and actor_id = " + actorId + ";");

        statement.close();
        connection.commit();
        connection.close();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/movies/{movieId}/director")
    public void deleteMovieDirector(@PathVariable long movieId) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/movies");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        statement.executeUpdate("update movies set director_id = null where id = " + movieId + ";");

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

    @RequestMapping(method = RequestMethod.GET, path = "/actors/{id}")
    public Actor getActor(@PathVariable long id) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/movies");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from actors where id = " + id + ";");
        Actor actor = null;
        while (resultSet.next()) {
            String firstName = resultSet.getString(1);
            String lastName = resultSet.getString(2);
            actor = new Actor(id, firstName, lastName);
        }

        resultSet.close();
        statement.close();
        connection.close();
        return actor;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/actors")
    public void addActor(@RequestBody Actor movie) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/movies");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        statement.executeUpdate("insert into actors (firstName, lastName)"
                + "values (" + movie.getFirstName() + ", " + movie.getLastName() + ";");
        statement.close();
        connection.commit();
        connection.close();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/actors/{id}")
    public void deleteActor(@PathVariable long id) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/movies");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from actors where id = " + id + ";");

        statement.close();
        connection.commit();
        connection.close();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/directors")
    public Director[] getDirectors() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/movies");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from directors;");
        ArrayList<Director> movies = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getLong(0);
            String firstName = resultSet.getString(1);
            String lastName = resultSet.getString(2);
            Director movie = new Director(id, firstName, lastName);
            movies.add(movie);
        }

        Director[] moviesArray = new Director[movies.size()];
        movies.toArray(moviesArray);
        resultSet.close();
        statement.close();
        connection.close();
        return moviesArray;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/directors/{id}")
    public Director getDirector(@PathVariable long id) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/movies");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from directors where id = " + id + ";");
        Director actor = null;
        while (resultSet.next()) {
            String firstName = resultSet.getString(1);
            String lastName = resultSet.getString(2);
            actor = new Director(id, firstName, lastName);
        }

        resultSet.close();
        statement.close();
        connection.close();
        return actor;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/directors")
    public void addDirector(@RequestBody Director movie) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/movies");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        statement.executeUpdate("insert into directors (firstName, lastName)"
                + "values (" + movie.getFirstName() + ", " + movie.getLastName() + ";");
        statement.close();
        connection.commit();
        connection.close();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/directors/{id}")
    public void deleteDirector(@PathVariable long id) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/movies");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from directors where id = " + id + ";");

        statement.close();
        connection.commit();
        connection.close();
    }
}
