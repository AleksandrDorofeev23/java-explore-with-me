package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.ParticipationRequestDto;

import java.util.Collection;

public interface RequestsService {

    Collection<ParticipationRequestDto> getById(long userId);

    ParticipationRequestDto create(long userId, long eventId);

    ParticipationRequestDto update(long userId, long requestId);
}
