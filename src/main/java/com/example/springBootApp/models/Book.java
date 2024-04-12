package com.example.springBootApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;


@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "people_id", referencedColumnName = "id")
    private Person owner;
    @Transient
    private boolean expired;
    @Column(name = "owned_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date owned_at;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    @Column(name = "name")
    private String name;
    @NotEmpty(message = "Author should not be empty")
    @Size(min = 1, max = 30, message = "Author should be between 1 and 30 characters")
    @Column(name = "author")
    private String author;
    @Max(value = 2024, message = "Year shouldn't be greater than 2024")
    @Column(name = "year_of_creation")
    private int yearOfCreation;
    public Book(){}
    public Book(String name, String author, int yearOfCreation, Date owned_at) {
        this.name = name;
        this.author = author;
        this.yearOfCreation = yearOfCreation;
        this.owned_at=owned_at;
    }

    public Date getOwned_at() {
        return owned_at;
    }

    public void setOwned_at(Date owned_at) {
        this.owned_at = owned_at;
    }

    public boolean getExpired(){ return expired;}
    public void setExpired(boolean expired){ this.expired=expired;}
    public Person getOwner(){ return owner;}

    public void setOwner(Person owner){this.owner = owner;}

    public int getId() {return id;}

    public String getName() {return name;}

    public String getAuthor() {return author;}

    public int getYearOfCreation() {return yearOfCreation;}

    public void setId(int id) {this.id = id;}

    public void setName(String name) {this.name = name;}

    public void setAuthor(String author) {this.author = author;}

    public void setYearOfCreation(int yearOfCreation) {this.yearOfCreation = yearOfCreation;}
}
