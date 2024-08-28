package com.example.astonhomeworks.hw2.controllers;

import com.example.astonhomeworks.hw2.dao.DirectorDAO;
import com.example.astonhomeworks.hw2.models.Director;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/directors")
public class DirectorController {

    private final DirectorDAO directorDAO;

    @Autowired
    public DirectorController(DirectorDAO directorDAO) {
        this.directorDAO = directorDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("directors", directorDAO.getDirectors());
        return "director/index";
    }

    @GetMapping("/{id}/edit")
    public String goToEditPage(@PathVariable("id") Integer id, Model model){
        model.addAttribute("director", directorDAO.getDirectorById(id));
        return "director/editPage";
    }

    @GetMapping("/newDirector")
    public String goToAddingPage(@ModelAttribute("director") Director director) {
        return "director/addingPage";
    }

    @GetMapping("/{id}")
    public String showCurrentDirector(@PathVariable("id") Integer id, Model model){
        model.addAttribute("director", directorDAO.getDirectorById(id));
        model.addAttribute("movies", directorDAO.getAllDirectorMovies(id));
        return "director/currentDirectorPage";
    }

    @PatchMapping("/{id}/edit")
    public String editDirector(@PathVariable("id") Integer id,
                               @ModelAttribute("director") Director director){
        directorDAO.updateDirector(director);
        return "redirect:/directors/" + id;
    }

    @PostMapping
    public String createDirector(@ModelAttribute("director") Director director){
        directorDAO.addDirector(director);
        return "redirect:/directors";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id){
        directorDAO.deleteDirector(id);
        return "redirect:/directors";
    }

//    @PatchMapping("/{id}")
//    public String removeMovieFromDirector(@PathVariable("id") Integer id) {
//
//        directorDAO.r
//
//        return "redirect:/directors/" + id;
//    }

}
