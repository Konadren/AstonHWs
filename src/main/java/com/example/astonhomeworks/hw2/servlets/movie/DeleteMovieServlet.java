package com.example.astonhomeworks.hw2.servlets.movie;

import com.example.astonhomeworks.hw2.dao.MovieDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteMovie")
public class DeleteMovieServlet extends HttpServlet {

    private MovieDAO movieDAO = new MovieDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int movieId = Integer.parseInt(req.getParameter("id"));

        try {
            movieDAO.deleteMovie(movieId);
            resp.sendRedirect("movies");
        } catch (Exception e) {
            throw new ServletException(e);
        }

    }
}
