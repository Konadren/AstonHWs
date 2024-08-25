package com.example.astonhomeworks.hw2.dao;

import com.example.astonhomeworks.hw2.models.AbstractAnimal;
import com.example.astonhomeworks.hw2.models.Cat;
import com.example.astonhomeworks.hw2.util.DatabaseUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SingleTableAnimalDAO {

    public void saveAnimal (AbstractAnimal animal) {
        Transaction transaction = null;
        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            session.persist(animal);

            transaction.commit();
        } catch (Exception e) {
            if (transaction == null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateAnimal(AbstractAnimal animal) {
        Transaction transaction = null;
        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();
            session.merge(animal);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteAnimal(int animalId) {
        Transaction transaction = null;
        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();
            AbstractAnimal animal = session.get(AbstractAnimal.class, animalId);
            if (animal != null) {
                session.remove(animal);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public AbstractAnimal getAnimalById(int animalId) {
        Transaction transaction = null;
        AbstractAnimal animal = null;
        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();
            animal = session.get(AbstractAnimal.class, animalId);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return animal;
    }

    // полиморфный запрос на всех животных
    @SuppressWarnings("unchecked")
    public List<AbstractAnimal> getAllAnimals() {
        Transaction transaction = null;
        List<AbstractAnimal> animals = null;
        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();
            animals = session.createQuery("FROM AbstractAnimal", AbstractAnimal.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return animals;
    }

    // полимофрный запрос только на кошек
    public List<Cat> getAllCats() {
        Transaction transaction = null;
        List<Cat> cats = null;

        try (Session session = DatabaseUtil.getSession()) {
            transaction = session.beginTransaction();

            // Запрос только для объектов Cat
            cats = session.createQuery("from Cat", Cat.class).getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return cats;
    }


}
