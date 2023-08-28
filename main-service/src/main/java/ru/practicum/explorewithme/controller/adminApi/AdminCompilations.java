package ru.practicum.explorewithme.controller.adminApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.dto.NewCompilationDto;
import ru.practicum.explorewithme.dto.UpdateCompilationRequest;
import ru.practicum.explorewithme.service.CompilationsService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/compilations")
public class AdminCompilations {

    private final CompilationsService compilationsService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Получен запрос @PostMapping(/admin/compilations)" + newCompilationDto.toString());
        return compilationsService.create(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long compId) {
        log.info("Получен запрос @PostMapping(/admin/compilations/{})", compId);
        compilationsService.delete(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto update(@PathVariable long compId,
                                 @Valid @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        log.info("Получен запрос @PatchMapping(/admin/compilations/{}) " + updateCompilationRequest.toString(), compId);
        return compilationsService.update(compId, updateCompilationRequest);
    }
}
