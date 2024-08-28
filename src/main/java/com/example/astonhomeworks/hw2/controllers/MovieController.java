package com.example.astonhomeworks.hw2.controllers;

import com.example.astonhomeworks.hw2.dao.ActorDAO;
import com.example.astonhomeworks.hw2.dao.MovieDAO;
import com.example.astonhomeworks.hw2.models.Actor;
import com.example.astonhomeworks.hw2.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieDAO movieDAO;
    private final ActorDAO actorDAO;

    @Autowired
    public MovieController(MovieDAO movieDAO, ActorDAO actorDAO) {
        this.movieDAO = movieDAO;
        this.actorDAO = actorDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("movies", movieDAO.getAllMovies());
        return "movie/index";
    }

    @GetMapping("/newMovie")
    public String goToAddingPage(@ModelAttribute("movie") Movie movie){
        return "movie/addingPage";
    }

    @PostMapping
    public String createMovie(@ModelAttribute("movie")  Movie movie,
                             BindingResult binding){
        //todo: валидатор
       // if (binding.hasErrors()) return "/books/addingPage";
        movieDAO.addMovie(movie);
        return "redirect:/movies";
    }

    @GetMapping("/{id}")
    public String showCurrentMovie(@PathVariable("id") Integer id, Model model,
                                  @ModelAttribute("actor") Actor actor){
        model.addAttribute("movie", movieDAO.getMovieById(id));
        model.addAttribute("actors", actorDAO.getActors());
        model.addAttribute("movieActors", movieDAO.getActorsByMovieId(id));

        return "movie/currentMoviePage";
    }

    @GetMapping("/{id}/edit")
    public String goToEditPage(Model model, @PathVariable("id") Integer id){
        model.addAttribute("movie", movieDAO.getMovieById(id));
        return  "movie/editPage";
    }

    @PatchMapping("/{id}")
    public String editMovie(@ModelAttribute("movie")  Movie movie,
                           BindingResult binding, @PathVariable("id") Integer id){

        //if (binding.hasErrors()) return "books/editPage";
        // todo: возможно ошибка
        movieDAO.updateMovie(movie);
        return "redirect:/movies/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable("id") Integer id){
        movieDAO.deleteMovie(id);
        return "redirect:/movies";
    }

    @PatchMapping("/{id}/removeActor")
    public String removeActorFromMovie(@PathVariable("id") Integer id,
                                       @RequestParam("actorId") Integer actorId){
        actorDAO.removeActorFromMovie(id, actorId);
        return "redirect:/movies/" + id;
    }

    @PatchMapping("/{id}/addActor")
    public String addActorToMovie(@PathVariable("id") Integer movieId,
                                  @RequestParam("actorId") Integer actorId) {
        System.out.println(actorId);
        movieDAO.addActorToMovie(movieId, actorId);
        return "redirect:/movies/" + movieId;
    }

}
