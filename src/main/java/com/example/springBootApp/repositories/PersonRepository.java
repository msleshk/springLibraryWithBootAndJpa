package com.example.springBootApp.repositories;

import com.example.springBootApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
