package ru.practicum.explorewithme.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class ExceptionsHandler {

    @ExceptionHandler
    @ResponseStatus(CONFLICT)
    public ApiError alreadyExistsException(final AlreadyExistsException e) {
        log.info(e.getMessage());
        List<String> trace = Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.toList());
        return new ApiError(null, e.getMessage(), CONFLICT, "Объект уже создан", LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(CONFLICT)
    public ApiError eventValidException(final EventValidException e) {
        log.info(e.getMessage());
        List<String> trace = Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.toList());
        return new ApiError(null, e.getMessage(), CONFLICT, "Неверные данные", LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public ApiError notFoundException(final NotFoundException e) {
        log.info(e.getMessage());
        List<String> trace = Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.toList());
        return new ApiError(null, e.getMessage(), NOT_FOUND, "Объект не найден", LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(CONFLICT)
    public ApiError relatedException(final RelatedException e) {
        log.info(e.getMessage());
        List<String> trace = Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.toList());
        return new ApiError(null, e.getMessage(), CONFLICT, "Категория связана с событием", LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(CONFLICT)
    public ApiError requestValidException(final RequestValidException e) {
        log.info(e.getMessage());
        List<String> trace = Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.toList());
        return new ApiError(null, e.getMessage(), CONFLICT, "Неверные данные", LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ApiError timeException(final TimeException e) {
        log.info(e.getMessage());
        List<String> trace = Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.toList());
        return new ApiError(null, e.getMessage(), BAD_REQUEST, "Неверное время", LocalDateTime.now());
    }
}
