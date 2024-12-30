package com.RentalBook.RentalBook.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.RentalBook.RentalBook.Dtos.AuthResponse;

import com.RentalBook.RentalBook.Dtos.RegisterData;
import com.RentalBook.RentalBook.Entity.User;
import com.RentalBook.RentalBook.Entity.Enum.Role;
import com.RentalBook.RentalBook.Repositories.UserRepository;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    //registering the new user
    public AuthResponse register(RegisterData data) {
        Optional<User> existingUser = userRepository.findByEmail(data.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with this email already exists!");
        }
        // Create a new user entity
        User user = new User();
        user.setFirstName(data.getFirstName());
        user.setLastName(data.getLastName());
        user.setEmail(data.getEmail());
        user.setPassword(passwordEncoder.encode(data.getPassword())); // Encrypt the password
        user.setRole(data.getRole() != null ? data.getRole() : Role.USER); // Default role to USER
       // Save the user to the database
        userRepository.save(user);
        return AuthResponse.builder().build();
    }

}
