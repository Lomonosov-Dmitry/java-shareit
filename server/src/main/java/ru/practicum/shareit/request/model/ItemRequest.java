package ru.practicum.shareit.request.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "requests")
public class ItemRequest {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    @Column(name = "description")
    private String description;
    @Column(name = "created")
    private LocalDateTime created;
    @Column(name = "user_id")
    private Integer userId;
}
