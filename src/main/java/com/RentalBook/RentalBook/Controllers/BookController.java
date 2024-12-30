package com.RentalBook.RentalBook.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.RentalBook.RentalBook.Dtos.AuthResponse;
import com.RentalBook.RentalBook.Dtos.BookUpdate;
import com.RentalBook.RentalBook.Dtos.UserId;
import com.RentalBook.RentalBook.Entity.Books;
import com.RentalBook.RentalBook.Services.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bookrental/books")
public class BookController {

     @Autowired
    private BookService bookService;
    
    // Posting a new book (admin only)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponse> addBook(@Valid @RequestBody BookUpdate book) {
        return ResponseEntity.ok(bookService.addBook(book));
    }

    // Updating a book (admin only)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @Valid @RequestBody BookUpdate book) {
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }

    // Deleting a book (admin only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }

     //Get all books(admin, user)
    @GetMapping
    public ResponseEntity<List<Books>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

     // Get book by id (admin, user)
    @GetMapping("/{id}")
    public ResponseEntity<Books> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }


    //get the all the books which are availabe for rent
    @GetMapping("/available")
    public ResponseEntity<List<Books>> getAvailableBooksCount() {
        //long count = bookRepository.countByAvailabilityStatus(Status.AVAILABLE);
        List<Books> list=bookService.getBooksAvailable();
        return ResponseEntity.ok(list);
    }
   
//this annotation is used to rent the book for the user 
    @PutMapping("/{bookId}/rent")
     public ResponseEntity<String> rentBook(@PathVariable Long bookId,@RequestBody UserId userId) {
        return bookService.rentBook(bookId,userId);
}

//this annotation is used to return the book 
@PutMapping("/{bookId}/return")
public ResponseEntity<String> returnBook(@PathVariable Long bookId,@RequestBody UserId userId) {
    return bookService.returnBook(bookId,userId);
}

 }
