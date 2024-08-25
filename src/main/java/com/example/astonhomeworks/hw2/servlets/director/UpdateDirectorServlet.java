package com.example.astonhomeworks.hw2.servlets.director;

import com.example.astonhomeworks.hw2.dao.DirectorDAO;
import com.example.astonhomeworks.hw2.models.Director;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/updateDirector")
public class UpdateDirectorServlet extends HttpServlet {
    private DirectorDAO directorDAO = new DirectorDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        int age = Integer.parseInt(req.getParameter("age"));

        Director director = new Director();
        director.setId(id);
        director.setName(name);
        director.setAge(age);

        try {
            directorDAO.updateDirector(director);
            resp.sendRedirect("directorDetails?id=" + id);
        } catch (Exception e) {
            throw new ServletException("Database error", e);
        }
    }
}