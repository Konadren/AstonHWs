package com.example.astonhomeworks.hw2.servlets.movie;

import com.example.astonhomeworks.hw2.dao.MovieDAO;
import com.example.astonhomeworks.hw2.models.Movie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/updateMovie")
public class UpdateMovieServlet extends HttpServlet {

    private MovieDAO movieDAO = new MovieDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int movieId = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        int releaseYear = Integer.parseInt(req.getParameter("releaseYear"));
        int directorId = Integer.parseInt(req.getParameter("director_id"));


        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setName(name);
        movie.setReleaseYear(releaseYear);
        movie.setOwner(directorId);

        try {
            // Обновляем информацию о фильме
            movieDAO.updateMovie(movie);

            // Перенаправляем пользователя на страницу деталей фильма
            resp.sendRedirect("movieDetails?id=" + movieId);

        } catch (SQLException | IOException e) {
            throw new ServletException("Error updating movie", e);
        }
    }
}
