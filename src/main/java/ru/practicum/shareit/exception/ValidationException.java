package ru.practicum.shareit.exception;

public class ValidationException extends RuntimeException {
    private String error;
    private String description;

    public ValidationException(String error, String description) {
        this.error = error;
        this.description = description;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }
}
