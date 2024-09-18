package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.practicum.shareit.request.model.ItemRequest;

@Data
@Entity
@Table(name = "items", schema = "public")
public class Item {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer id;

     @Column(nullable = false)
     private String name;

     @Column(nullable = false)
     private String description;

     @Column(name = "is_available", nullable = false)
     private Boolean available;

     @JoinColumn(name = "owner_id", nullable = false)
     private Integer ownerId;

     @ManyToOne
     @JoinColumn(name = "request_id")
     private ItemRequest request;
}
