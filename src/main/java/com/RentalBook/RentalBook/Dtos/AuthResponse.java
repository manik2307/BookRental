package com.RentalBook.RentalBook.Dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class AuthResponse {
  public AuthResponse(String string) {
        //TODO Auto-generated constructor stub
    }

private final String message = "Success";
}
