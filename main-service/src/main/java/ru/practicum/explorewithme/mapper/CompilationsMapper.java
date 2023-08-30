package ru.practicum.explorewithme.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.*;
import ru.practicum.explorewithme.model.Compilation;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationsMapper {

    private final EventMapper eventMapper;

    public Compilation toModel(NewCompilationDto newCompilationDto) {
        if (newCompilationDto == null) {
            return null;
        }
        Compilation compilation = new Compilation();
        compilation.setTitle(newCompilationDto.getTitle());
        compilation.setPinned(newCompilationDto.getPinned());
        return compilation;
    }

    public CompilationDto toDto(Compilation compilation) {
        if (compilation == null) {
            return null;
        }
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setTitle(compilation.getTitle());
        compilationDto.setId(compilation.getId());
        compilationDto.setPinned(compilation.getPinned());
        List<EventShortDto> eventList = null;
        if (compilation.getEvents() != null) {
            eventList = compilation.getEvents().stream().map(eventMapper::toShortDto).collect(Collectors.toList());
        }
        compilationDto.setEvents(eventList);
        return compilationDto;
    }
}
