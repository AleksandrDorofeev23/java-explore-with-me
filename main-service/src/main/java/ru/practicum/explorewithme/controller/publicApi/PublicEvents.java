package ru.practicum.explorewithme.controller.publicApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.service.EventsService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.practicum.explorewithme.utils.Constants.DATA_TIME_FORMAT;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/events")
public class PublicEvents {

    private final EventsService eventsService;

    @GetMapping
    public Collection<EventShortDto> getAllPublic(@RequestParam(required = false) String text,
                                                  @RequestParam(required = false) List<Long> categories,
                                                  @RequestParam(required = false) boolean paid,
                                                  @RequestParam(required = false)
                                                  @DateTimeFormat(pattern = DATA_TIME_FORMAT) LocalDateTime rangeStart,
                                                  @RequestParam(required = false)
                                                  @DateTimeFormat(pattern = DATA_TIME_FORMAT) LocalDateTime rangeEnd,
                                                  @RequestParam(defaultValue = "false") boolean onlyAvailable,
                                                  @RequestParam(required = false) String sort,
                                                  @RequestParam(defaultValue = "0") @Min(0) int from,
                                                  @RequestParam(defaultValue = "10") @Min(1) int size,
                                                  HttpServletRequest httpServletRequest) {
        log.info("Получен запрос @GetMapping(/events) {} {} {} {} {} {} {} {} {}", text, categories, paid, rangeStart,
                rangeEnd, onlyAvailable, sort, from, size);
        return eventsService.getAllPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, httpServletRequest);
    }

    @GetMapping("/{id}")
    public EventFullDto getById(@PathVariable long id, HttpServletRequest httpServletRequest) {
        log.info("Получен запрос @GetMapping(/events/{})", id);
        return eventsService.getById(id, httpServletRequest);
    }
}
