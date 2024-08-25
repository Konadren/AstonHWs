package com.example.astonhomeworks.hw2.dao;


import com.example.astonhomeworks.hw2.models.Movie;
import com.example.astonhomeworks.hw2.models.Actor;
import com.example.astonhomeworks.hw2.util.DatabaseUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ActorDAO {

    public List<Actor> getActors() {
        List<Actor> actors = null;
        Transaction transaction;

        try (Session session = DatabaseUtil.getSession()){
            transaction = session.beginTransaction();

            actors = session.createQuery("FROM Actor", Actor.class).list();

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return actors;
    }

    public void addActor(Actor actor) {
        Transaction transaction = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            session.persist(actor);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Actor getActorById(int id) {
        Transaction transaction = null;
        Actor actor = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            actor = session.get(Actor.class, id);
            if (actor  == null) throw new NoSuchElementException("Актера с айди = " + id + " не существует в БД");

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return actor;
    }

    public void updateActor(Actor actor) {
        Transaction transaction = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            Actor actorFromDatabase = session.get(Actor.class, actor.getId());

            // если не будет этой конструкции, то актер обновится, но удалится из actor_movie
            // наверное дело в том, что у actor, поступаемого в метод, не сеттается его movies (=null)
            // ну и код факапится
            if (actor.getMovies() == null) {
                actor.setMovies(actorFromDatabase.getMovies()); // а здесь костыльно мы стягиваем из БД фильмы актера
            } else {
                actor.getMovies().addAll(actorFromDatabase.getMovies());
            }

            session.merge(actor);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteActor(int id) {
        Transaction transaction = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            Actor actor = session.get(Actor.class, id);
            if (actor == null) throw new NoSuchElementException("Актёра с айди = " + id + " нет в БД");

            session.remove(actor);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Movie> getMoviesByActorId(int actorId) {
        Transaction transaction = null;
        List<Movie> movies = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            String hqlQuery = "SELECT m FROM Movie m JOIN m.actors a WHERE a.id = :actorId";
            Query<Movie> query = session.createQuery(hqlQuery, Movie.class);
            query.setParameter("actorId", actorId);

            movies = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return movies;
    }

    public void removeActorFromMovie(int actorId, int movieId) {
        Transaction transaction = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            Movie movie = session.get(Movie.class, movieId);
            Actor actor = session.get(Actor.class, actorId);

            if (movie == null) {
                throw new RuntimeException("Фильма с айди = " + movieId + " не существует в БД.");
            }
            if (actor == null) {
                throw new RuntimeException("Актера с айди =  " + actorId + " не существует в БД.");
            }

            movie.getActors().remove(actor);
            actor.getMovies().remove(movie);

            session.merge(movie);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}