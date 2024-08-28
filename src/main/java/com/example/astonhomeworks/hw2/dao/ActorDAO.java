package com.example.astonhomeworks.hw2.dao;


import com.example.astonhomeworks.hw2.models.Movie;
import com.example.astonhomeworks.hw2.models.Actor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class ActorDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public ActorDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional(readOnly = true)
    public List<Actor> getActors() {
        return getCurrentSession().createQuery("FROM Actor", Actor.class).list();
    }

    @Transactional
    public void addActor(Actor actor) {
        getCurrentSession().persist(actor);
    }

    @Transactional(readOnly = true)
    public Actor getActorById(int id) {
        return getCurrentSession().get(Actor.class, id);
    }


    @Transactional
    public void updateActor(Actor actor) {
        Actor actorFromDatabase = getCurrentSession().get(Actor.class, actor.getId());

        // если не будет этой конструкции, то актер обновится, но удалится из actor_movie
        // наверное дело в том, что у actor, поступаемого в метод, не сеттается его movies (=null)
        // ну и код факапится
        if (actor.getMovies() == null) {
            actor.setMovies(actorFromDatabase.getMovies()); // а здесь костыльно мы стягиваем из БД фильмы актера
        } else {
            actor.getMovies().addAll(actorFromDatabase.getMovies());
        }

        getCurrentSession().merge(actor);
    }

    @Transactional
    public void deleteActor(int id) {
        Actor actor = getCurrentSession().get(Actor.class, id);

        if (actor == null) throw new NoSuchElementException("Актёра с айди = " + id + " нет в БД");

        getCurrentSession().remove(actor);
    }

    @Transactional(readOnly = true)
    public List<Movie> getMoviesByActorId(int actorId) {
        String hqlQuery = "SELECT m FROM Movie m JOIN FETCH m.owner JOIN m.actors a WHERE a.id = :actorId";
        Query<Movie> query = getCurrentSession().createQuery(hqlQuery, Movie.class);
        query.setParameter("actorId", actorId);

        return query.getResultList();
    }

    @Transactional
    public void removeActorFromMovie(int movieId, int actorId) {
        Movie movie = getCurrentSession().get(Movie.class, movieId);
        Actor actor = getCurrentSession().get(Actor.class, actorId);

        movie.getActors().remove(actor);
        actor.getMovies().remove(movie);

        getCurrentSession().merge(movie);
    }
}