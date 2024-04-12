package com.example.springBootApp.services;

import com.example.springBootApp.models.Person;
import com.example.springBootApp.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }
    public List<Person> getPeople(){
       return personRepository.findAll();
    }
    public Person getPerson(int id){
        Optional<Person> person= personRepository.findById(id);
        return person.orElse(null);
    }
    @Transactional
    public void save(Person person){
        personRepository.save(person);
    }
    @Transactional
    public void delete(int id){
        personRepository.deleteById(id);
    }

}
