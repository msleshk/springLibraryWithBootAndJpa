package com.example.springBootApp.repositories;


import com.example.springBootApp.models.Book;
import com.example.springBootApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Modifying
    @Query("UPDATE Book b SET b.owner = :owner WHERE b.id = :id")
    void updateBookOwner(@Param("owner") Person owner, @Param("id") int id);
    @Modifying
    @Query("UPDATE Book b SET b.owner = null WHERE b.id = :id")
    void deleteBookOwner(@Param("id") int id);
    List<Book> findBooksByOwner(Person owner);
    List<Book> findBooksByNameContainingIgnoreCase(String name);
}
