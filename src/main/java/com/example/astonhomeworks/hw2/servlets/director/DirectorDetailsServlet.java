package com.example.astonhomeworks.hw2.servlets.director;

import com.example.astonhomeworks.hw2.dao.DirectorDAO;
import com.example.astonhomeworks.hw2.models.Director;
import com.example.astonhomeworks.hw2.models.Movie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/directorDetails")
public class DirectorDetailsServlet extends HttpServlet {
    private DirectorDAO directorDAO = new DirectorDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String directorIdStr = req.getParameter("id");
        int directorId = Integer.parseInt(directorIdStr);

        try {
            List<Movie> movies = directorDAO.getAllDirectorMovies(directorId);
            Director director = directorDAO.getDirectorById(directorId);
            req.setAttribute("director", director);
            req.setAttribute("movies", movies);
            req.setAttribute("directorId", directorId);
            req.getRequestDispatcher("director-details.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int directorId = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        int releaseYear = Integer.parseInt(req.getParameter("releaseYear"));

        Movie movie = new Movie();
        movie.setName(name);
        movie.setReleaseYear(releaseYear);
        movie.setOwner(directorId);

        try {
            directorDAO.addMovieToDirector(movie, directorId);
            resp.sendRedirect("directorDetails?id=" + directorId);
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }


}
