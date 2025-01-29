package ru.practicum.shareit.item.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Comment;

import java.util.Collection;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Integer> {
    public Collection<Comment> findAllByItemId(Integer itemId);
}
