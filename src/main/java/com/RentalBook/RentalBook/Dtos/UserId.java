package com.RentalBook.RentalBook.Dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserId {
    @NotNull(message = "User ID cannot be null")
    private Long userId;
}
