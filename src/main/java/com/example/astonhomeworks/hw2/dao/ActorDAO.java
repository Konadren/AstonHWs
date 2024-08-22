package com.example.astonhomeworks.hw2.dao;


import com.example.astonhomeworks.hw2.models.Movie;
import com.example.astonhomeworks.hw2.models.Actor;
import com.example.astonhomeworks.hw2.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActorDAO {

    public List<Actor> getAllActors() throws SQLException {
        String query = "SELECT * FROM Actor";
        List<Actor> actors = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
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

    public void addActor(Actor actor) throws SQLException {
        String query = "INSERT INTO Actor (name, age) VALUES (?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, actor.getName());
            pstmt.setInt(2, actor.getAge());
            pstmt.executeUpdate();
        }
    }

    public Actor getActorById(int id) throws SQLException {
        String query = "SELECT * FROM Actor WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Actor(rs.getInt("id"), rs.getString("name"), rs.getInt("age"));
            }
            return null;
        }
    }

    public void updateActor(Actor actor) throws SQLException {
        String query = "UPDATE Actor SET name = ?, age = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, actor.getName());
            pstmt.setInt(2, actor.getAge());
            pstmt.setInt(3, actor.getId());
            pstmt.executeUpdate();
        }
    }

    public void deleteActor(int id) throws SQLException {
        String query = "DELETE FROM Actor WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public List<Movie> getMoviesByActorId(int actorId) throws SQLException {
        String query = "SELECT m.* FROM Movie m JOIN Actor_Movie am ON m.id = am.movie_id WHERE am.actor_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, actorId);
            ResultSet rs = pstmt.executeQuery();
            List<Movie> movies = new ArrayList<>();
            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getString("name"),
                        rs.getInt("releaseYear"),
                        rs.getInt("id"),
                        rs.getInt("director_id"));
                movies.add(movie);
            }
            return movies;
        }
    }

    public void removeActorFromMovie(int actorId, int movieId) throws SQLException {
        String query = "DELETE FROM actor_movie WHERE actor_id = ? AND movie_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, actorId);
            pstmt.setInt(2, movieId);
            pstmt.executeUpdate();
        }
    }
}