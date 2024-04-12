package com.example.springBootApp.services;

import com.example.springBootApp.models.Book;
import com.example.springBootApp.models.Person;
import com.example.springBootApp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PersonService personService;

    @Autowired
    public BookService(BookRepository bookRepository, PersonService personService) {
        this.bookRepository = bookRepository;
        this.personService = personService;
    }
    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }
    public Book findOne(int id){
        Optional<Book> book= bookRepository.findById(id);
        return book.orElse(null);
    }
    public List<Book> findAll(Integer page, Integer books_per_page, Boolean sort_by_year){
        if ((page!=null && books_per_page!=null)&& sort_by_year!=null){
            return bookRepository.findAll(PageRequest.of(page, books_per_page, Sort.by("yearOfCreation").descending())).getContent();
        }
        if (page!=null && books_per_page!=null){
            return bookRepository.findAll(PageRequest.of(page, books_per_page)).getContent();
        }
        if (sort_by_year!=null){
            return bookRepository.findAll(Sort.by("yearOfCreation").descending());
        }
        return bookRepository.findAll();
    }
    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }
    @Transactional
    public void updateOwner(Book book){
        book.setOwned_at(new Date());
        bookRepository.save(book);
        bookRepository.updateBookOwner(book.getOwner(), book.getId());
    }
    @Transactional
    public void deleteOwner(Book book){
        book.setOwned_at(null);
        book.setExpired(false);
        bookRepository.deleteBookOwner(book.getId());
    }
    public List<Book> getOwnedBooks(int id){
        Person owner=personService.getPerson(id);
        List<Book> ownedBooks= bookRepository.findBooksByOwner(owner);
        for (int i=0; i< ownedBooks.size();i++){
            Date dateOfOwning=ownedBooks.get(i).getOwned_at();
            Date currentDate=new Date();
            long diffInMillies=Math.abs(currentDate.getTime()-dateOfOwning.getTime());
            long diffInDays=diffInMillies/(24*60*60*1000);
            if (diffInDays>10)
                ownedBooks.get(i).setExpired(true);
        }
        return ownedBooks;
    }
    public List<Book> search(String title){
        return bookRepository.findBooksByNameContainingIgnoreCase(title);
    }
}
