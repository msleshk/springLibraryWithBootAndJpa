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
@RequestMapping("/books")
public class BooksController {
    BookService bookService;
    PersonService personService;

    @Autowired
    public BooksController(BookService bookService, PersonService personService){
        this.bookService=bookService;
        this.personService=personService;
    }
    @GetMapping("/search")
    public String searchBooks(@RequestParam(value = "title", required = false) String book_title, Model model){
        if (book_title!=null){
            model.addAttribute("books", bookService.search(book_title));
            model.addAttribute("title", book_title);
        }
        return "books/search";
    }

    @GetMapping()
    public String showBooks(@RequestParam(value = "books_per_page", required = false) Integer books_per_page,
                            @RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "sort_by_year", required = false) Boolean sort_by_year,
                            Model model){
        model.addAttribute("books", bookService.findAll(page, books_per_page, sort_by_year));
        return "books/showAll";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model){
        Book book=bookService.findOne(id);
        Person person=book.getOwner();
        model.addAttribute("book", book);
        model.addAttribute("person", person);
        model.addAttribute("people", personService.getPeople());
        return "books/show";
    }
    @PatchMapping("{id}/addBookOwner")
    public String addBookOwner(@ModelAttribute("book") Book book, @RequestParam("personId") int personId){
        book=bookService.findOne(book.getId());
        book.setOwner(personService.getPerson(personId));
        bookService.updateOwner(book);
        return "redirect:/books/{id}";
    }
    @PatchMapping("{id}/freeBook")
    public String freeTheBook(@PathVariable("id") int id, @ModelAttribute("book") Book book){
        bookService.deleteOwner(book);
        return "redirect:/books/{id}";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "books/new";
        }
        bookService.save(book);
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookService.findOne(id));
        return "/books/edit";
    }
    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "books/edit";
        bookService.save(book);
        return "redirect:/books";
    }
}
