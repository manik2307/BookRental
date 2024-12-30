package com.RentalBook.RentalBook.Dtos;

import org.hibernate.annotations.processing.Pattern;

import com.RentalBook.RentalBook.Entity.Enum.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
//add validations like for email and everything 
@Data
public class RegisterData {
  @NotBlank(message = "First Name is mandatory")
    @Size(min = 2, max = 50, message = "First Name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last Name is mandatory")
    @Size(min = 2, max = 50, message = "Last Name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    // Role is optional, so no validation annotation
    private Role role; // Assuming Role is an enum or a class
}