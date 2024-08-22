package com.example.astonhomeworks.hw2.dao;

import com.example.astonhomeworks.hw2.models.Director;
import com.example.astonhomeworks.hw2.models.Movie;
import com.example.astonhomeworks.hw2.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DirectorDAO {
    public List<Movie> getAllDirectorMovies(int id) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM Movie WHERE director_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

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

    public void addMovieToDirector(Movie movie, int directorId) throws SQLException {
        String query = "INSERT INTO Movie(name, releaseYear, director_id) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, movie.getName());
            pstmt.setInt(2, movie.getReleaseYear());
            pstmt.setInt(3, directorId);

            pstmt.executeUpdate();
        }
    }

    public List<Director> getDirectors() throws SQLException {
        List<Director> directors = new ArrayList<>();
        String query = "SELECT * FROM Director";

        try (Connection conn = DatabaseUtil.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Director director = new Director();
                director.setId(rs.getInt("id"));
                director.setName(rs.getString("name"));
                director.setAge(rs.getInt("age"));

                directors.add(director);
            }
        }
        return directors;
    }

    public void addDirector(Director director) throws SQLException {
        String query = "INSERT INTO Director (name, age) VALUES (?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, director.getName());
            pstmt.setInt(2, director.getAge());

            pstmt.executeUpdate();
        }
    }

    public void updateDirector(Director director) throws SQLException {
        String query = "UPDATE Director SET name = ?, age = ? WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, director.getName());
            pstmt.setInt(2, director.getAge());
            pstmt.setInt(3, director.getId());

            pstmt.executeUpdate();
        }
    }

    public Director getDirectorById(int id) throws SQLException {
        String query = "SELECT * FROM Director WHERE id = ?";
        Director director = new Director();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    director.setId(rs.getInt("id"));
                    director.setName(rs.getString("name"));
                    director.setAge(rs.getInt("age"));
                }
            } catch (SQLException e) {
                throw new SQLException(e);
            }
        }
        return director;
    }

    public void deleteDirector(int directorId) throws SQLException {
        String query = "DELETE FROM Director WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, directorId);
            pstmt.executeUpdate();
        }
    }

}
