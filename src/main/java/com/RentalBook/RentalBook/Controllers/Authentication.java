package com.RentalBook.RentalBook.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.RentalBook.RentalBook.Dtos.AuthResponse;

import com.RentalBook.RentalBook.Dtos.RegisterData;
import com.RentalBook.RentalBook.Services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bookrental")
public class Authentication {
    
    @Autowired
    private AuthService authservice;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterData data)
    {
        return ResponseEntity.ok(authservice.register(data));
    }
}
