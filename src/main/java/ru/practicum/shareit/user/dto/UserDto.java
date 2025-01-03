package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
    int id;
    @NotBlank
    String name;
    @NotBlank
    @NotNull
    @Email
    String email;
}
