package ru.practicum.explorewithme.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class ExceptionsHandler {

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public TimeException timeExceptionExp(final TimeException e) {
        log.info(e.getMessage());
        return new TimeException(e.getMessage());
    }
}
