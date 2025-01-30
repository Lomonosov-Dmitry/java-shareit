package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "item_id", nullable = false)
    private Integer itemId;
    @Column(name = "comment", nullable = false)
    private String comment;
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
}
