package com.example.astonhomeworks.hw2.servlets.movie;

import com.example.astonhomeworks.hw2.dao.ActorDAO;
import com.example.astonhomeworks.hw2.dao.MovieDAO;
import com.example.astonhomeworks.hw2.models.Actor;
import com.example.astonhomeworks.hw2.models.Movie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/movieDetails")
public class MovieDetailsServlet extends HttpServlet {

    private MovieDAO movieDAO = new MovieDAO();
    private ActorDAO actorDAO = new ActorDAO();


    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int movieId = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("movieId", movieId);

        try {
            List<Actor> actorsInMovie = movieDAO.getActorsByMovieId(movieId);
            List<Actor> allActors = actorDAO.getAllActors();

            req.setAttribute("actorsInMovie", actorsInMovie);
            req.setAttribute("allActors", allActors);

            Movie movie = movieDAO.getMovieById(movieId);
            req.setAttribute("movie", movie);
            req.getRequestDispatcher("movie-details.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int movieId = Integer.parseInt(req.getParameter("movieId"));
        int actorId = Integer.parseInt(req.getParameter("actorId"));

        try {
            movieDAO.addActorToMovie(actorId, movieId);
            resp.sendRedirect("movieDetails?id=" + movieId);

        } catch (SQLException e) {
            throw new ServletException("Error adding actor to movie", e);
        }
    }

}
