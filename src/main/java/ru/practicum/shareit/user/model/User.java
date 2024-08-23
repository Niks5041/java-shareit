package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */

@Data
public class User {
     private Integer id;
     private String name;
     @Email(message = "Email должен содержать '@'")
     @NotNull(message = "Email не может быть null")
     private String email;
}
