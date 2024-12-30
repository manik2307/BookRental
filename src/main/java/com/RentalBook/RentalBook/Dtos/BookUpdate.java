package com.RentalBook.RentalBook.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class BookUpdate {

     @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Author cannot be blank")
    @Size(max = 50, message = "Author cannot exceed 50 characters")
    private String author;

    @NotBlank(message = "Genre cannot be blank")
    @Size(max = 30, message = "Genre cannot exceed 30 characters")
    private String genre;
}
