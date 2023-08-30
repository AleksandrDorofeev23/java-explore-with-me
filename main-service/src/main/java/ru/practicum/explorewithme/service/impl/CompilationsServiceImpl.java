package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.dto.NewCompilationDto;
import ru.practicum.explorewithme.dto.UpdateCompilationRequest;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.CompilationsMapper;
import ru.practicum.explorewithme.model.Compilation;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.repository.CompilationsRepository;
import ru.practicum.explorewithme.repository.EventsRepository;
import ru.practicum.explorewithme.service.CompilationsService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationsServiceImpl implements CompilationsService {

    private final CompilationsRepository compilationsRepository;
    private final CompilationsMapper compilationsMapper;
    private final EventsRepository eventsRepository;

    @Override
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationsMapper.toModel(newCompilationDto);
        List<Event> events;
        if (newCompilationDto.getEvents() != null) {
            events = eventsRepository.findByIdIn(newCompilationDto.getEvents());
            compilation.setEvents(events);
        }
        compilation = compilationsRepository.save(compilation);
        return compilationsMapper.toDto(compilation);
    }

    @Override
    public void delete(Long compId) {
        compilationsRepository.deleteById(compId);
    }

    @Override
    public CompilationDto update(Long compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilationOld = compilationsRepository.findById(compId).orElseThrow(()
                -> new NotFoundException("Такой подборки нет"));
        List<Event> events;
        if (updateCompilationRequest.getEvents() != null) {
            events = eventsRepository.findByIdIn(updateCompilationRequest.getEvents());
            compilationOld.setEvents(events);
        }
        if (updateCompilationRequest.getTitle() != null) {
            compilationOld.setTitle(updateCompilationRequest.getTitle());
        }
        compilationOld.setPinned(updateCompilationRequest.getPinned());
        compilationOld = compilationsRepository.save(compilationOld);
        return compilationsMapper.toDto(compilationOld);
    }

    @Override
    public Collection<CompilationDto> getAll(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        if (pinned) {
            return compilationsRepository.findByPinned(pinned, pageable).stream().map(compilationsMapper::toDto).collect(Collectors.toList());
        } else {
            return compilationsRepository.findAll(pageable).stream().map(compilationsMapper::toDto).collect(Collectors.toList());
        }
    }

    @Override
    public CompilationDto getById(Long compId) {
        Compilation compilation = compilationsRepository.findById(compId).orElseThrow(()
                -> new NotFoundException("Такой подборки нет"));
        return compilationsMapper.toDto(compilation);
    }
}
