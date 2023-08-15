package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.HitDto;
import ru.practicum.explorewithme.dto.StatsDto;
import ru.practicum.explorewithme.service.HitsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import static ru.practicum.explorewithme.utils.Constants.DATA_TIME_FORMAT;

@RestController
@RequiredArgsConstructor
@Slf4j
public class
HitsController {

    private final HitsService hitsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody HitDto hitDto) {
        log.info("Получен запрос @PostMapping(/hit) " + hitDto.toString());
        hitsService.create(hitDto);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public Collection<StatsDto> getByParameters(@RequestParam @DateTimeFormat(pattern = DATA_TIME_FORMAT) LocalDateTime start,
                                                @RequestParam @DateTimeFormat(pattern = DATA_TIME_FORMAT) LocalDateTime end,
                                                @RequestParam(required = false) String[] uris,
                                                @RequestParam(defaultValue = "false") boolean unique) {
        log.info("Получен запрос @GetMapping(/stats)" + " start = " + start +
                " end = " + end + " uris = " + Arrays.toString(uris));
        return hitsService.getByParameters(start, end, uris, unique);
    }
}
