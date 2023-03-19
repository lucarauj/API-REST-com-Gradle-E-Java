package com.lucarauj.booksapi.controllers;

import com.lucarauj.booksapi.models.Book;
import com.lucarauj.booksapi.models.CreateBookRequest;
import com.lucarauj.booksapi.models.UpdateBookRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BooksControllers {

    private final List<Book> books = new ArrayList<>();

    @GetMapping
    public List<Book> getBooks() {
        return books;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookId(@PathVariable UUID id) {

        Optional<Book> existingBook = books.stream().filter(book -> book.getId().equals(id)).findFirst();

        if(existingBook.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(existingBook.get());
    }

    @PostMapping
    public Book createBook(@RequestBody CreateBookRequest request) {

        Book book = new Book(UUID.randomUUID(), request.getTitle(), request.getDescription(), request.getAuthor());
        books.add(book);

        return book;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable UUID id, @RequestBody UpdateBookRequest request) {

        Optional<Book> existingBook = books.stream().filter(book -> book.getId().equals(id)).findFirst();

        if(existingBook.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Book bookToUpdate = existingBook.get();

        if(request.getTitle() != null) {
            bookToUpdate.setTitle(request.getTitle());
        }

        if(request.getDescription() != null) {
            bookToUpdate.setDescription(request.getDescription());
        }

        if(request.getAuthor() != null) {
            bookToUpdate.setAuthor(request.getAuthor());
        }

        return  ResponseEntity.ok(bookToUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable UUID id) {

        boolean wasDeleted = books.removeIf(book -> book.getId().equals(id));

        if(wasDeleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}
