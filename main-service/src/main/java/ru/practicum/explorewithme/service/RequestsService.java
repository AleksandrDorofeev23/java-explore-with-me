package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.ParticipationRequestDto;

import java.util.Collection;

public interface RequestsService {

    Collection<ParticipationRequestDto> getById(Long userId);

    ParticipationRequestDto create(Long userId, Long eventId);

    ParticipationRequestDto update(Long userId, Long requestId);
}
