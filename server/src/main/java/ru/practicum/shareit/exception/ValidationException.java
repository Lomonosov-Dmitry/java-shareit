package ru.practicum.shareit.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
    private final String error;
    private final String description;

    public ValidationException(String error, String description) {
        this.error = error;
        this.description = description;
    }
}
