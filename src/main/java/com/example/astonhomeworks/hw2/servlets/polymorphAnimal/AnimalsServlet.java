package com.example.astonhomeworks.hw2.servlets.polymorphAnimal;

import com.example.astonhomeworks.hw2.dao.SingleTableAnimalDAO;
import com.example.astonhomeworks.hw2.models.AbstractAnimal;
import com.example.astonhomeworks.hw2.models.Cat;
import com.example.astonhomeworks.hw2.models.Dog;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/animals")
public class AnimalsServlet extends HttpServlet {

    private SingleTableAnimalDAO dao = new SingleTableAnimalDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<AbstractAnimal> animals;
        try {
            // Получение всех животных из DAO
            animals = dao.getAllAnimals();

            req.setAttribute("animals", animals);

            req.getRequestDispatcher("animals.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error retrieving animals", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        String name = req.getParameter("name");
        String rating = req.getParameter("rating");

        // блок условий, насколько я понимаю, лучше не делать
        // но уже супер лень создавать отдельные сервлеты
        try {
            AbstractAnimal animal;
            if ("cat".equalsIgnoreCase(type)) {
                int hauteurRating = Integer.parseInt(rating);
                animal = new Cat();
                animal.setName(name);
                ((Cat) animal).setHauteurRating(hauteurRating);
            } else if ("dog".equalsIgnoreCase(type)) {
                int loyaltyRating = Integer.parseInt(rating);
                animal = new Dog();
                animal.setName(name);
                ((Dog) animal).setLoyaltyRating(loyaltyRating);
            } else {
                throw new IllegalArgumentException("Invalid animal type");
            }

            dao.saveAnimal(animal);

            // Перенаправление на страницу со списком животных
            resp.sendRedirect("/animals");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Failed to add animal");
            req.getRequestDispatcher("/create-animal.jsp").forward(req, resp);
        }
    }

}
