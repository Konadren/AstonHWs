package com.example.astonhomeworks.hw2.servlets.movie;

import com.example.astonhomeworks.hw2.dao.DirectorDAO;
import com.example.astonhomeworks.hw2.dao.MovieDAO;
import com.example.astonhomeworks.hw2.models.Director;
import com.example.astonhomeworks.hw2.models.Movie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/updateMovie")
public class UpdateMovieServlet extends HttpServlet {

    private MovieDAO movieDAO = new MovieDAO();
    private DirectorDAO directorDAO = new DirectorDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int movieId = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        int releaseYear = Integer.parseInt(req.getParameter("releaseYear"));
        int directorId = Integer.parseInt(req.getParameter("director_id"));

        Movie movie = new Movie();
        Director director = directorDAO.getDirectorById(directorId);

        movie.setId(movieId);
        movie.setName(name);
        movie.setReleaseYear(releaseYear);
        movie.setOwner(director);

        try {
            movieDAO.updateMovie(movie);
            resp.sendRedirect("movieDetails?id=" + movieId);
        } catch (Exception e) {
            throw new ServletException("Error updating movie", e);
        }
    }
}
