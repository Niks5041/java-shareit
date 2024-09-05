package ru.practicum.shareit.item.model;

import lombok.Data;
import ru.practicum.shareit.request.model.ItemRequest;

@Data
public class Item {
     private Integer id;
     private String name;
     private String description;
     private Boolean available;
     private Integer owner;
     private ItemRequest request;
}
