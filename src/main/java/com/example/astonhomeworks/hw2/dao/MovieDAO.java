package com.example.astonhomeworks.hw2.dao;

import com.example.astonhomeworks.hw2.models.Actor;
import com.example.astonhomeworks.hw2.models.Movie;
import com.example.astonhomeworks.hw2.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM Movie";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Movie movie = new Movie();

                movie.setId(rs.getInt("id"));
                movie.setName(rs.getString("name"));
                movie.setReleaseYear(rs.getInt("releaseYear"));
                movie.setOwner(rs.getInt("director_id"));

                movies.add(movie);
            }
        }

        return movies;
    }

    public void addMovie(Movie movie) throws SQLException {
        String query = "INSERT INTO Movie (name, releaseYear, director_id) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, movie.getName());
            pstmt.setInt(2, movie.getReleaseYear());
            pstmt.setInt(3, movie.getOwner());

            pstmt.executeUpdate();
        }
    }

    public Movie getMovieById(int movieId) throws SQLException {
        String query = "SELECT * FROM Movie WHERE id=?";
        Movie movie = new Movie();
        
        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, movieId);
            
            try (ResultSet rs = pstmt.executeQuery()){
                while (rs.next()) {
                    movie.setId(rs.getInt("id"));
                    movie.setName(rs.getString("name"));
                    movie.setReleaseYear(rs.getInt("releaseYear"));
                    movie.setOwner(rs.getInt("director_id"));
                }
            } catch (SQLException e) {
                throw new SQLException(e);
            }
        }
        return movie;
    }

    public void updateMovie(Movie movie) throws SQLException {
        String query = "UPDATE Movie SET name = ?, releaseYear = ?, director_id = ? WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, movie.getName());
            pstmt.setInt(2, movie.getReleaseYear());
            pstmt.setInt(3, movie.getOwner());
            pstmt.setInt(4, movie.getId());

            pstmt.executeUpdate();
        }

    }

    public void deleteMovie(int movieId) throws SQLException {
        String query = "DELETE FROM Movie WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, movieId);
            pstmt.executeUpdate();
        }
    }

    public List<Actor> getActorsByMovieId(int movieId) throws SQLException {
        List<Actor> actors = new ArrayList<>();
        String query = "SELECT Actor.id, Actor.name, Actor.age FROM Actor " +
                "JOIN Actor_Movie ON Actor.id = Actor_Movie.actor_id " +
                "WHERE Actor_Movie.movie_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, movieId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Actor actor = new Actor();
                actor.setId(rs.getInt("id"));
                actor.setName(rs.getString("name"));
                actor.setAge(rs.getInt("age"));
                actors.add(actor);
            }
        }
        return actors;
    }

    public void addActorToMovie(int actorId, int movieId) throws SQLException {
        String query = "INSERT INTO Actor_Movie (actor_id, movie_id) VALUES (?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, actorId);
            pstmt.setInt(2, movieId);
            pstmt.executeUpdate();
        }
    }

}
