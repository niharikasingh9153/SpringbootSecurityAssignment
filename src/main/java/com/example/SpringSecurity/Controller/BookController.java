package com.example.SpringSecurity.Controller;

import com.example.SpringSecurity.model.Book;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController() {
        books.add(new Book(1L, "1984", "George Orwell", "9780451524935", true));
        books.add(new Book(2L, "The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", true));
    }

    @GetMapping("/public")
    public List<Book> publicBooks() {
        return books;
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping
    public List<Book> getBooks() {
        return books;
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return books.stream().filter(b -> b.getId().equals(id)).findFirst().orElse(null);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/{id}/reserve")
    public String reserveBook(@PathVariable Long id) {
        return "Book " + id + " reserved.";
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping
    public String addBook(@RequestBody Book book) {
        books.add(book);
        return "Book added.";
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PutMapping("/{id}")
    public String updateBook(@PathVariable Long id, @RequestBody Book updated) {
        return "Book updated.";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        return "Book deleted.";
    }

    @PostAuthorize("returnObject != null")
    @GetMapping("/secure/{id}")
    public Book secureGet(@PathVariable Long id) {
        return books.stream().filter(b -> b.getId().equals(id)).findFirst().orElse(null);
    }
}

