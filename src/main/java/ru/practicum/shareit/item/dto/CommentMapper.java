package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Comment;

public class CommentMapper {

    public static CommentDto mapToDto(Comment comment, String authorName) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setText(comment.getComment());
        dto.setAuthorName(authorName);
        dto.setCreated(comment.getCreated());
        return dto;
    }
}
