package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.HitDto;
import ru.practicum.explorewithme.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.Collection;

public interface HitsService {

    void create(HitDto hitDto);

    Collection<StatsDto> getByParameters(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique);
}
