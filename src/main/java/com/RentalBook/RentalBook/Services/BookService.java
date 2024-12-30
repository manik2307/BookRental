package com.RentalBook.RentalBook.Services;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.RentalBook.RentalBook.Dtos.AuthResponse;
import com.RentalBook.RentalBook.Dtos.BookUpdate;
import com.RentalBook.RentalBook.Dtos.UserId;
import com.RentalBook.RentalBook.Entity.Books;
import com.RentalBook.RentalBook.Entity.User;
import com.RentalBook.RentalBook.Entity.Enum.Status;

import com.RentalBook.RentalBook.Repositories.BookRepository;
import com.RentalBook.RentalBook.Repositories.UserRepository;

import lombok.Data;

@Service
public class BookService {
     @Autowired
    private BookRepository bookRepository;
     @Autowired
     private UserRepository userRepository;

    // Add a new book
    public AuthResponse addBook(BookUpdate book) {
        Books Book=new Books();
        //set the status to Avaialable
        Book.setAvailabilityStatus(Status.Available);
        Book.setAuthor(book.getAuthor());
        Book.setGenre(book.getGenre());
        Book.setTitle(book.getTitle());
        bookRepository.save(Book);
        return AuthResponse.builder().build();
    }

    // Update book details
    public String updateBook(Long id, BookUpdate book) {
        Optional<Books> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            Books updatedBook = existingBook.get();
            updatedBook.setTitle(book.getTitle());
            updatedBook.setAuthor(book.getAuthor());
            updatedBook.setGenre(book.getGenre());
            bookRepository.save(updatedBook);
            return "SuccessFully Updated the Book";
        } else {
            throw new RuntimeException("Book not found");
        }
    }

    // Delete book by id
    public void deleteBook(Long id) {
        //this method check whether the book is present 
        Books book=getBookById(id);
        //also check whether the book is rented or not if rented thrwo an error
        if (book.getAvailabilityStatus() == Status.Rented) {
            throw new RuntimeException("Book is Rented By other User");
        }
        bookRepository.deleteById(book.getId());
    }

    // Get all books
    public List<Books> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get book by id
    public Books getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    // Get books that are avalable for rent
    public List<Books> getBooksAvailable() {
        //find all the books that are avalable for rent and retuen that
       return bookRepository.findByAvailabilityStatus(Status.Available);  
    }

    //get a book for rent by the user 
    public ResponseEntity<String> rentBook(Long bookId,UserId userid)
    {
        Long userId=userid.getUserId();
        // Check whether the user ID is valid
    Optional<User> userOptional = userRepository.findById(userId);
    if (!userOptional.isPresent()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID.");
    }
    User user = userOptional.get();

    // Check whether the user has less than 2 books rented
    if (user.getRentedBooks().size() >= 2) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can rent a maximum of 2 books.");
    }

    // Check whether the book ID is valid
    Optional<Books> bookOptional = bookRepository.findById(bookId);
    if (!bookOptional.isPresent()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid book ID.");
    }
    Books book = bookOptional.get();

    // Check if the book is available for rent
    if (book.getAvailabilityStatus() != Status.Available) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book is not available for rent.");
    }

    // Rent the book: Add the book to the user's rented books
    user.getRentedBooks().add(book);

    // Change the book status to rented
    book.setAvailabilityStatus(Status.Rented);

    // Save the updated user and book entities
    userRepository.save(user);
    bookRepository.save(book);
        
    return ResponseEntity.ok("Book rented successfully!");
    }


    // Return book form the usr and cahnge the status
    public ResponseEntity<String> returnBook(Long bookId, UserId userid) {
        Long userId=userid.getUserId();
        // Step 1: Check if the user exists
    Optional<User> userOptional = userRepository.findById(userId);
    if (!userOptional.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
    User user = userOptional.get();
    
    // Step 2: Check if the book exists
    Optional<Books> bookOptional = bookRepository.findById(bookId);
    if (!bookOptional.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
    }
    Books book = bookOptional.get();

    // Step 3: Check if the user has rented this book
    if (!user.getRentedBooks().contains(book)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User has not rented this book");
    }

    // Step 4: Remove the book from the user's rented books list
    user.getRentedBooks().remove(book);

    // Step 5: Change the availability status of the book to AVAILABLE
    book.setAvailabilityStatus(Status.Available);

    // Step 6: Save the updated user and book
    userRepository.save(user);  // Save user after removing the book
    bookRepository.save(book);  // Save book after updating status

    return ResponseEntity.ok("Book returned successfully!");
     }

}
