package com.example.astonhomeworks.hw2.controllers;

import com.example.astonhomeworks.hw2.dao.ActorDAO;
import com.example.astonhomeworks.hw2.models.Actor;
import com.example.astonhomeworks.hw2.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/actors")
public class ActorController {

    private final ActorDAO actorDAO;

    @Autowired
    public ActorController(ActorDAO actorDAO) {
        this.actorDAO = actorDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("actors", actorDAO.getActors());

        return "actor/index";
    }

    @GetMapping("/{id}")
    public String showActorDetails(@PathVariable("id") Integer id, Model model,
                                   @ModelAttribute("actor") Actor actor){
        model.addAttribute("actor", actorDAO.getActorById(id));
        model.addAttribute("movies", actorDAO.getMoviesByActorId(id));

        return "actor/currentActorPage";
    }

    @GetMapping("/newActor")
    public String goToAddingPage(@ModelAttribute("actor") Actor actor){
        return "actor/addingPage";
    }

    @PostMapping()
    public String createActor(@ModelAttribute("actor") Actor actor,
                              BindingResult binding) {
        actorDAO.addActor(actor);

        return "redirect:/actors";
    }

    @GetMapping("/{id}/edit")
    public String goToEditPage(Model model, @PathVariable("id") Integer id){
        model.addAttribute("actor", actorDAO.getActorById(id));
        return  "actor/editPage";
    }

    @PatchMapping("/{id}")
    public String editActor(@ModelAttribute("actor") Actor actor,
                            BindingResult binding, @PathVariable("id") Integer id){
        actorDAO.updateActor(actor);
        return "redirect:/actors/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteActor(@PathVariable("id") Integer id) {
        actorDAO.deleteActor(id);
        return "redirect:/actors";
    }

//    @PatchMapping("/{id}/deleteFromMovie")
//    public String removeFromMovie(@PathVariable("id") Integer actorId) {
//
//
//        actorDAO.removeActorFromMovie(actorId, );
//    }
}
