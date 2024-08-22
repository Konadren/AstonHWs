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
import java.util.List;

@WebServlet("/directors")
public class DirectorsServlet extends HttpServlet {

    private DirectorDAO directorDAO = new DirectorDAO();

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Director> directors = directorDAO.getDirectors();
            req.setAttribute("directors", directors);
            req.getRequestDispatcher("directors.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        int age = Integer.parseInt(req.getParameter("age"));

        Director director = new Director();
        director.setName(name);
        director.setAge(age);

        try {
            directorDAO.addDirector(director);
            resp.sendRedirect("directors");
        } catch (SQLException e) {
            throw new ServletException(e);
        }

    }


}
