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
import java.util.List;

@WebServlet("/movies")
public class MoviesServlet extends HttpServlet {

    private MovieDAO movieDAO = new MovieDAO();
    private DirectorDAO directorDAO = new DirectorDAO();


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Movie> movies = movieDAO.getAllMovies();
            req.setAttribute("movies", movies);
            req.getRequestDispatcher("movies.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        int releaseYear = Integer.parseInt(req.getParameter("releaseYear"));
        int director_id = Integer.parseInt(req.getParameter("director_id"));

        Movie movie = new Movie();
        Director director = directorDAO.getDirectorById(director_id);

        movie.setName(name);
        movie.setReleaseYear(releaseYear);
        movie.setOwner(director);

        try {
            movieDAO.addMovie(movie);
            resp.sendRedirect("movies");
        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

}
