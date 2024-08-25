package com.example.astonhomeworks.hw2.servlets.actor;

import com.example.astonhomeworks.hw2.dao.ActorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/removeActor")
public class RemoveActorFromFilmServlet extends HttpServlet {

    private ActorDAO actorDAO = new ActorDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int movieId = Integer.parseInt(req.getParameter("movieId"));
        int actorId = Integer.parseInt(req.getParameter("actorId"));

        try {
            actorDAO.removeActorFromMovie(actorId, movieId);
            resp.sendRedirect("movieDetails?id=" + movieId);
        } catch (Exception e) {
            throw new ServletException(e);
        }

    }
}
