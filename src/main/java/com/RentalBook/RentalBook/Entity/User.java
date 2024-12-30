package com.RentalBook.RentalBook.Entity;

//import com.BookRental.BookRental.Entites.Enum.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.RentalBook.RentalBook.Entity.Enum.Role;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   private String firstName;

   @Column( nullable = false)
   private String lastName;

   @Column(unique = true, nullable = false)
   private String email;

   @Column(nullable = false)
   private String password;
   
   @Column(nullable = false)
   @Enumerated(EnumType.STRING)
   private Role role;

   @ManyToMany
    @JoinTable(
        name = "user_book",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
   List<Books> rentedBooks=new ArrayList<>();

   
   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
   }

   @Override
   public String getUsername() {
       return email;
   }

   @Override
   public String getPassword() {
       return password;
   }

   @Override
   public boolean isAccountNonExpired() {
       return true;
   }

   @Override
   public boolean isAccountNonLocked() {
       return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
       return true;
   }

   @Override
   public boolean isEnabled() {
       return true;
   }
}
