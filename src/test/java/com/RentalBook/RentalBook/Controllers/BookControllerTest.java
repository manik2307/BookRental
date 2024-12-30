package com.RentalBook.RentalBook.Controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.RentalBook.RentalBook.Dtos.AuthResponse;
import com.RentalBook.RentalBook.Dtos.BookUpdate;
import com.RentalBook.RentalBook.Entity.Books;
import com.RentalBook.RentalBook.Entity.Enum.Status;
import com.RentalBook.RentalBook.Services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void testGetAllBooks() throws Exception {
        // Mock data
        List<Books> booksList = Arrays.asList(
            new Books(1L, "Book One", "Author One", "Anything", Status.Available),
            new Books(2L, "Book Two", "Author Two", "Anything", Status.Available)
        );

        // Mock service behavior
        when(bookService.getAllBooks()).thenReturn(booksList);

        // Perform GET request
        mockMvc.perform(get("/bookrental/books"))
                .andExpect(status().isOk()) // Assert status 200 OK
                .andExpect(jsonPath("$.size()").value(2)) // Assert size of returned list
                .andExpect(jsonPath("$[0].title").value("Book One")) // Assert first book title
                .andExpect(jsonPath("$[1].availabilityStatus").value("Available")); // Assert second book status
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    public void testAddBook() throws Exception {
        // Mock request body
        BookUpdate newBook = new BookUpdate("Book Three", "Author", "Anything");

        // Mock response
        AuthResponse response = new AuthResponse("Book added successfully");
        when(bookService.addBook(any(BookUpdate.class))).thenReturn(response);

        // Perform POST request
        mockMvc.perform(post("/bookrental/books")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newBook))) // Serialize request body
                .andExpect(status().isOk()) // Assert status 200 OK
                .andExpect(jsonPath("$.message").value("Book added successfully")); // Assert response message
    }
}
