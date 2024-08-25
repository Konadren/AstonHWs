package com.example.astonhomeworks.hw2.servlets.actor;

import com.example.astonhomeworks.hw2.dao.ActorDAO;
import com.example.astonhomeworks.hw2.models.Actor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/actors")
public class ActorsServlet extends HttpServlet {

    private ActorDAO actorDAO = new ActorDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            List<Actor> actors = actorDAO.getActors();
            req.setAttribute("actors", actors);
            req.getRequestDispatcher("actors.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        int age = Integer.parseInt(req.getParameter("age"));


        Actor actor = new Actor();
        actor.setName(name);
        actor.setAge(age);

        try {
            actorDAO.addActor(actor);
            resp.sendRedirect("actors");
        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

}
