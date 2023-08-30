package ru.practicum.explorewithme.controller.priavteApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.service.RequestsService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users/{userId}/requests")
public class PrivateRequests {

    private final RequestsService requestsService;

    @GetMapping
    public Collection<ParticipationRequestDto> getById(@PathVariable Long userId) {
        log.info("Получен запрос @GetMapping(/users/{}/requests)", userId);
        return requestsService.getById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto create(@PathVariable Long userId, @RequestParam Long eventId) {
        log.info("Получен запрос @PostMapping(/users/{}/requests) {}", userId, eventId);
        return requestsService.create(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto update(@PathVariable Long userId, @PathVariable Long requestId) {
        log.info("Получен запрос @PatchMapping(/users/{}/requests/{}/cancel)", userId, requestId);
        return requestsService.update(userId, requestId);
    }
}
