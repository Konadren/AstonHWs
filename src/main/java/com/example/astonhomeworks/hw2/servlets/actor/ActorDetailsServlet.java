package com.example.astonhomeworks.hw2.servlets.actor;

import com.example.astonhomeworks.hw2.dao.ActorDAO;
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

@WebServlet("/actorDetails")
public class ActorDetailsServlet extends HttpServlet {

    private ActorDAO actorDAO = new ActorDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int actorId = Integer.parseInt(req.getParameter("id"));

        try {
            Actor actor = actorDAO.getActorById(actorId);

            List<Movie> movies = actorDAO.getMoviesByActorId(actorId);

            // Пихаем данные в JSP
            req.setAttribute("actor", actor);
            req.setAttribute("movies", movies);

            req.getRequestDispatcher("/actorDetails.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException("Error retrieving actor details", e);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        int age = Integer.parseInt(req.getParameter("age"));

        Actor actor = new Actor();
        actor.setId(id);
        actor.setName(name);
        actor.setAge(age);

        try {
            actorDAO.updateActor(actor);
            resp.sendRedirect("actorDetails?id=" + id);
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }


}
