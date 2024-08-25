package com.example.astonhomeworks.hw2.servlets.actor;

import com.example.astonhomeworks.hw2.dao.ActorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/deleteActor")
public class DeleteActorServlet extends HttpServlet {

    private ActorDAO actorDAO = new ActorDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int actorId = Integer.parseInt(req.getParameter("id"));

        try {
            actorDAO.deleteActor(actorId);
            resp.sendRedirect("actors"); // Редирект на список всех режиссеров после удаления
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}
