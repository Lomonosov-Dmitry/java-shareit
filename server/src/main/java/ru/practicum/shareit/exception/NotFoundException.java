package ru.practicum.shareit.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final String error;
    private final String description;

    public NotFoundException(String error, String description) {
        this.error = error;
        this.description = description;
    }
}
