package com.example.astonhomeworks.hw2.dao;

import com.example.astonhomeworks.hw2.models.Animal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AnimalDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public AnimalDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public void saveAnimal (Animal animal) {
        getCurrentSession().persist(animal);
    }

    @Transactional
    public void updateAnimal(Animal animal) {
       getCurrentSession().merge(animal);
    }

    @Transactional
    public void deleteAnimal(int animalId) {
        Animal animal = getCurrentSession().get(Animal.class, animalId);
        getCurrentSession().remove(animal);
    }

    @Transactional(readOnly = true)
    public Animal getAnimalById(int animalId) {
        return getCurrentSession().get(Animal.class, animalId);
    }

    // полиморфный запрос на всех животных
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Animal> getAllAnimals() {
        return getCurrentSession().createQuery("FROM Animal", Animal.class).getResultList();
    }
}
