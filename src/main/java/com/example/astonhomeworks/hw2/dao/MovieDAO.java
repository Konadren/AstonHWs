package com.example.astonhomeworks.hw2.dao;

import com.example.astonhomeworks.hw2.models.Actor;
import com.example.astonhomeworks.hw2.models.Movie;
import com.example.astonhomeworks.hw2.util.DatabaseUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MovieDAO {
    public List<Movie> getAllMovies() {
        List<Movie> movies = null;
        Transaction transaction = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            // Насколько я понимаю, при этом запросе должны делаться ещё уйма других запросов
            // которые будут стягивать режиссёров
            // но из-за того, что у меня стоит FetchType.LAZY этого не происходит
            // можно было бы использовать JOIN FETCH, как в DirectorDAO, если бы заместо LAZY был EAGER
            movies = session.createQuery("FROM Movie", Movie.class).list();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return movies;
    }

    public void addMovie(Movie movie) {
        Transaction transaction = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            session.persist(movie);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Movie getMovieById(int movieId) {
        Transaction transaction = null;
        Movie movie = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            movie = session.get(Movie.class, movieId);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return movie;
    }

    public void updateMovie(Movie movie) {
        Transaction transaction = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            session.merge(movie);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteMovie(int movieId) {
        Transaction transaction = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            Movie movie = session.get(Movie.class, movieId);
            if (movie == null) throw new NoSuchElementException("Фильма с айди = " + movieId + " нет");

            session.remove(movie);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Actor> getActorsByMovieId(int movieId) {
        Transaction transaction = null;
        List<Actor> actors = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            // HQL запрос для получения фильмов по ID актера
            String hqlQuery = "SELECT a FROM Movie m JOIN m.actors a WHERE m.id = :movieId";
            Query<Actor> query = session.createQuery(hqlQuery, Actor.class);
            query.setParameter("movieId", movieId);

            actors = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return actors;
    }

    public void addActorToMovie(int actorId, int movieId) {
        Transaction transaction = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            Movie movie = session.get(Movie.class, movieId);
            Actor actor = session.get(Actor.class, actorId);

            if (movie == null) {
                throw new RuntimeException("Movie with ID " + movieId + " not found.");
            }
            if (actor == null) {
                throw new RuntimeException("Actor with ID " + actorId + " not found.");
            }


            if (movie.getActors().isEmpty()) {
                movie.setActors(new ArrayList<>(List.of(actor)));
            } else {
                movie.getActors().add(actor);
            }

            if(actor.getMovies().isEmpty()) {
                actor.setMovies(new ArrayList<>(List.of(movie)));
            } else {
                actor.getMovies().add(movie);
            }

            session.persist(movie);

            transaction.commit();
        } catch (Exception e) {
            System.out.println("EXCEPTION");
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


}
