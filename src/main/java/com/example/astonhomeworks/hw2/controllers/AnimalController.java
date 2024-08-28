package com.example.astonhomeworks.hw2.controllers;


import com.example.astonhomeworks.hw2.dao.AnimalDAO;
import com.example.astonhomeworks.hw2.models.Animal;
import com.example.astonhomeworks.hw2.models.Cat;
import com.example.astonhomeworks.hw2.models.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/animals")
public class AnimalController {

    private final AnimalDAO animalDAO;

    @Autowired
    public AnimalController(AnimalDAO animalDAO) {
        this.animalDAO = animalDAO;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("animals", animalDAO.getAllAnimals());
        return "animal/index";
    }

    // Используем RequestParam для определения типа животного
    @GetMapping("/add")
    public String showAddAnimalForm(@RequestParam(value = "type", required = false, defaultValue = "Cat") String type, Model model) {
        Animal animal;
        if ("Dog".equalsIgnoreCase(type)) {
            animal = new Dog();
        } else {
            animal = new Cat();
        }
        model.addAttribute("animal", animal);
        return "animal/addingAnimalPage";
    }

    @PostMapping
    public String addAnimal(@ModelAttribute("animal") Animal animal) {
        animalDAO.saveAnimal(animal);
        return "redirect:/animals";
    }

    @GetMapping("/{id}")
    public String goToAnimalDetails(@PathVariable("id") Integer id, Model model) {
        Animal animal = animalDAO.getAnimalById(id);
        model.addAttribute("animal", animal);
        return "animal/currentAnimalPage";
    }
}

