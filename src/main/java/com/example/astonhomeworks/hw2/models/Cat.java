package com.example.astonhomeworks.hw2.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CAT")
public class Cat extends Animal {

    @Column(name = "hauteurRating")
    private int hauteurRating;

    public Cat() {
    }

    @Override
    public String getType(){
        return "Cat";
    }

    public int getHauteurRating() {
        return hauteurRating;
    }

    public void setHauteurRating(int hauteurRating) {
        this.hauteurRating = hauteurRating;
    }
}
