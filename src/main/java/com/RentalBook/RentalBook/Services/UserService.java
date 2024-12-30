package com.RentalBook.RentalBook.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.RentalBook.RentalBook.Entity.User;
import com.RentalBook.RentalBook.Repositories.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;
    
      // Get all users
      public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Get user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> u=userRepository.findByEmail(username);
        if(!u.isPresent())
        {
            throw new RuntimeException("User with this email does not exists!");
        }
        return u.get();
    }
    
}
