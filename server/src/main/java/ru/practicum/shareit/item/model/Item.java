package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.model.Request;

@Data
@Entity
@Table(name = "items", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
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
     private Request request;
}
