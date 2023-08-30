package ru.practicum.explorewithme.controller.publicApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.service.CompilationsService;

import javax.validation.constraints.Min;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/compilations")
public class PublicCompilations {

    private final CompilationsService compilationsService;

    @GetMapping
    public Collection<CompilationDto> getAll(@RequestParam(defaultValue = "false") Boolean pinned,
                                             @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                             @RequestParam(defaultValue = "10") @Min(1) Integer size) {
        log.info("Получен запрос @GetMapping(/compilations) {} {} {}", pinned, from, size);
        return compilationsService.getAll(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getById(@PathVariable Long compId) {
        log.info("Получен запрос @GetMapping(/compilations/{})", compId);
        return compilationsService.getById(compId);
    }
}
