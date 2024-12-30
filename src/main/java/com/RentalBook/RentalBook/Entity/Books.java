package com.RentalBook.RentalBook.Entity;



import java.util.ArrayList;
import java.util.List;

import com.RentalBook.RentalBook.Entity.Enum.Status;

//import com.BookRental.BookRental.Entites.Enum.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String genre;
    
     @Enumerated(EnumType.STRING)
    @Column( nullable = false)
    private Status availabilityStatus;
     
    @ManyToMany(mappedBy = "rentedBooks")
    List<User> users=new ArrayList<>();
    
    public Books(Long id, String title, String author, String genre, Status availabilityStatus) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availabilityStatus = availabilityStatus;
    }
}
