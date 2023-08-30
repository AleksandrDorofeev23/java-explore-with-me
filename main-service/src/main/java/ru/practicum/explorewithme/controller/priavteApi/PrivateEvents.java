package ru.practicum.explorewithme.controller.priavteApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.*;
import ru.practicum.explorewithme.service.EventsService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users/{userId}/events")
public class PrivateEvents {

    private final EventsService eventsService;

    @GetMapping
    public Collection<EventShortDto> getAllByUser(@PathVariable Long userId,
                                                  @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                                  @RequestParam(defaultValue = "10") @Min(1) Integer size) {
        log.info("Получен запрос @GetMapping(/users/{}/events) {} {}", userId, from, size);
        return eventsService.getAllByUser(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable Long userId,
                               @RequestBody @Valid NewEventDto newEventDto) {
        log.info("Получен запрос @PostMapping(/users/{}/events)" + newEventDto.toString(), userId);
        return eventsService.create(userId, newEventDto);
    }

    @GetMapping("{eventId}")
    public EventFullDto getByUserById(@PathVariable Long userId,
                                      @PathVariable Long eventId) {
        log.info("Получен запрос @GetMapping(/users/{}/events/{})", userId, eventId);
        return eventsService.getByUserById(userId, eventId);
    }

    @PatchMapping("{eventId}")
    public EventFullDto updateByUser(@PathVariable Long userId,
                                     @PathVariable Long eventId,
                                     @RequestBody @Valid UpdateEventUserRequest updateEventUserRequest) {
        log.info("Получен запрос @PatchMapping(/users/{}/events/{})" + updateEventUserRequest.toString(), userId, eventId);
        return eventsService.updateByUser(userId, eventId, updateEventUserRequest);
    }

    @GetMapping("{eventId}/requests")
    public Collection<ParticipationRequestDto> getRequestsByEventByUser(@PathVariable Long userId,
                                                                        @PathVariable Long eventId) {
        log.info("Получен запрос @GetMapping(/users/{}/events/{}/requests)", userId, eventId);
        return eventsService.getRequestsByEventByUser(userId, eventId);
    }

    @PatchMapping("{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequestsStatuses(@PathVariable Long userId,
                                                                 @PathVariable Long eventId,
                                                                 @RequestBody @Valid EventRequestStatusUpdateRequest requestsStatuses) {
        log.info("Получен запрос @PatchMapping(/users/{}/events/{}/requests)" + requestsStatuses.toString(), userId, eventId);
        return eventsService.updateRequests(userId, eventId, requestsStatuses);
    }
}
