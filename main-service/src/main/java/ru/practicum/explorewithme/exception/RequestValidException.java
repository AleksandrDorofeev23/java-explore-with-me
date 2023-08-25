package ru.practicum.explorewithme.exception;

public class RequestValidException extends RuntimeException {

    public RequestValidException(String message) {
        super(message);
    }
}
