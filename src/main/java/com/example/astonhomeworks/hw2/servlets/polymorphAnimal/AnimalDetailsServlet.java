package com.example.astonhomeworks.hw2.servlets.polymorphAnimal;

import com.example.astonhomeworks.hw2.dao.SingleTableAnimalDAO;
import com.example.astonhomeworks.hw2.models.AbstractAnimal;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/animalDetails")
public class AnimalDetailsServlet extends HttpServlet {

    private SingleTableAnimalDAO dao = new SingleTableAnimalDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int animalId = Integer.parseInt(req.getParameter("id"));


        AbstractAnimal animal;
        try {

            animal = dao.getAnimalById(animalId);

            req.setAttribute("animal", animal);

            req.getRequestDispatcher("animalDetails.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error retrieving animal details", e);
        }
    }
}