package com.example.ServiceSubscription.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "Name is mandatory")
    @Size(min=2, max = 100, message =
            "The name cannot be shorter than 2 and longer than 100 characters.")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(min=2, max = 255, message =
            "The email address length cannot be less than 2 characters and exceed 255 characters.")
    private String email;
}
