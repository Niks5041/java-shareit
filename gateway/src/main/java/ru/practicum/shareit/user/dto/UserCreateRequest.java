package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreateRequest {
    private String name;
    @Email(message = "Email должен содержать '@'")
    @NotNull(message = "Email не может быть null")
    private String email;
}