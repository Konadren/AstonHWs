package com.example.astonhomeworks.hw2.models;

import jakarta.persistence.*;

@Entity
@Table(name = "animal")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "animal_type")
public abstract class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_seq")
    @SequenceGenerator(name = "animal_seq", sequenceName = "animal_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "name")
    private String name;

    public Animal() {
    }

    @Transient
    public abstract String getType();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
