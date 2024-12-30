package com.RentalBook.RentalBook.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RentalBook.RentalBook.Entity.Books;
import com.RentalBook.RentalBook.Entity.Enum.Status;



@Repository
public interface BookRepository extends JpaRepository<Books,Long>{
    List<Books> findByAvailabilityStatus(Status available);
} 
