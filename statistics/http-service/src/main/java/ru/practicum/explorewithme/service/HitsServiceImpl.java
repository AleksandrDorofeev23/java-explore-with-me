package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.HitDto;
import ru.practicum.explorewithme.dto.StatsDto;
import ru.practicum.explorewithme.mapper.HitMapper;
import ru.practicum.explorewithme.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.Collection;

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
        if (unique) {
            return hitRepository.getStatsUnique(start, end, uris);
        } else {
            return hitRepository.getStats(start, end, uris);
        }
    }

}
