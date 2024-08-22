package com.example.astonhomeworks.hw2.servlets.director;

import com.example.astonhomeworks.hw2.dao.DirectorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteDirector")
public class DeleteDirectorServlet extends HttpServlet {

    private DirectorDAO directorDAO = new DirectorDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int directorId = Integer.parseInt(req.getParameter("id"));

        try {
            directorDAO.deleteDirector(directorId);
            resp.sendRedirect("directors"); // Редирект на список всех режиссеров после удаления
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}