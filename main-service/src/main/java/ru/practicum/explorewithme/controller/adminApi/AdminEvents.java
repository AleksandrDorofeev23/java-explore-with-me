package ru.practicum.explorewithme.controller.adminApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.UpdateEventAdminRequest;
import ru.practicum.explorewithme.service.EventsService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.practicum.explorewithme.utils.Constants.DATA_TIME_FORMAT;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/events")
public class AdminEvents {

    private final EventsService eventsService;

    @GetMapping()
    public Collection<EventFullDto> getAllAdmin(@RequestParam(required = false) List<Long> users,
                                                @RequestParam(required = false) List<String> states,
                                                @RequestParam(required = false) List<Long> categories,
                                                @RequestParam(required = false)
                                                @DateTimeFormat(pattern = DATA_TIME_FORMAT) LocalDateTime rangeStart,
                                                @RequestParam(required = false)
                                                @DateTimeFormat(pattern = DATA_TIME_FORMAT) LocalDateTime rangeEnd,
                                                @RequestParam(defaultValue = "0") @Min(0) int from,
                                                @RequestParam(defaultValue = "10") @Min(1) int size) {
        log.info("Получен запрос @GetMapping(/admin/events) {} {} {} {} {} {} {}", users, states, categories, rangeStart,
                rangeEnd, from, size);
        return eventsService.getAllAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@PathVariable long eventId,
                               @Valid @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("Получен запрос @PatchMapping(/admin/events/{})" + updateEventAdminRequest.toString(), eventId);
        return eventsService.update(eventId, updateEventAdminRequest);
    }
}
