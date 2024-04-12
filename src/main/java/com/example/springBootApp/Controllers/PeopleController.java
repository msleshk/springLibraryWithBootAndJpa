package com.example.springBootApp.Controllers;

import com.example.springBootApp.models.Book;
import com.example.springBootApp.models.Person;
import com.example.springBootApp.services.BookService;
import com.example.springBootApp.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonService personService;
    private final BookService bookService;
    @Autowired
    public PeopleController(PersonService personService, BookService bookService){
        this.personService = personService;
        this.bookService = bookService;
    }
    @GetMapping()
    public String showAll(Model model){
        model.addAttribute("people", personService.getPeople());
        return "people/showAll";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("book") Book book){
        model.addAttribute("person", personService.getPerson(id));
        model.addAttribute("books", bookService.getOwnedBooks(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "people/new";
        personService.save(person);
        return "redirect:/people";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id){
        personService.delete(id);
        return "redirect:/people";
    }

    @GetMapping ("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personService.getPerson(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "people/edit";
        }
        personService.save(person);
        return "redirect:/people";
    }

}
