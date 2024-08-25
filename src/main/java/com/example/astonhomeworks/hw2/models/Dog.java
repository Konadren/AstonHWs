package com.example.astonhomeworks.hw2.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DOG")
public class Dog extends AbstractAnimal{

    @Column(name = "loyaltyRating")
    private int loyaltyRating;

    public Dog() {
    }

    @Override
    public String getType(){
        return "Dog";
    }

    public int getLoyaltyRating() {
        return loyaltyRating;
    }

    public void setLoyaltyRating(int loyaltyRating) {
        this.loyaltyRating = loyaltyRating;
    }
}
