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
import java.util.List;

@WebServlet("/movies")
public class MoviesServlet extends HttpServlet {

    private MovieDAO movieDAO = new MovieDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Movie> movies = movieDAO.getAllMovies();
            req.setAttribute("movies", movies);
            req.getRequestDispatcher("movies.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        int releaseYear = Integer.parseInt(req.getParameter("releaseYear"));
        int director_id = Integer.parseInt(req.getParameter("director_id"));

        Movie movie = new Movie();
        movie.setName(name);
        movie.setReleaseYear(releaseYear);
        movie.setOwner(director_id);

        try {
            movieDAO.addMovie(movie);
            resp.sendRedirect("movies");
        } catch (SQLException e) {
            throw new ServletException(e);
        }

    }

}
