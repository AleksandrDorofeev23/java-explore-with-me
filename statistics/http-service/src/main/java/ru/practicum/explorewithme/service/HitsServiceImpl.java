package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.HitDto;
import ru.practicum.explorewithme.dto.StatsDto;
import ru.practicum.explorewithme.exception.TimeException;
import ru.practicum.explorewithme.mapper.HitMapper;
import ru.practicum.explorewithme.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HitsServiceImpl implements HitsService {

    private final HitRepository hitRepository;
    private final HitMapper hitMapper;

    @Override
    public void create(HitDto hitDto) {
        hitRepository.save(hitMapper.toHit(hitDto));
    }

    @Override
    public Collection<StatsDto> getByParameters(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        if (end.isBefore(start)) {
            throw new TimeException("Дата начала должна быть раньше даты окончания");
        }
        List<String> urisList;
        if (uris != null) {
            urisList = List.of(uris);
        } else {
            urisList = null;
        }
        if (unique) {
            return hitRepository.getStatsUnique(start, end, urisList);
        } else {
            return hitRepository.getStats(start, end, urisList);
        }
    }

}
