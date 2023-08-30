package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.dto.StatsDto;
import ru.practicum.explorewithme.model.Hit;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {

    @Query("SELECT new ru.practicum.explorewithme.dto.StatsDto(hit.app, hit.uri, COUNT(DISTINCT hit.ip) AS c) FROM Hit hit "
            + "WHERE hit.timestamp BETWEEN :start AND :end AND (hit.uri IN :uris OR :uris IS NULL) GROUP BY hit.app, hit.uri ORDER BY c DESC")
    Collection<StatsDto> getStatsUnique(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.explorewithme.dto.StatsDto(hit.app, hit.uri, COUNT(hit.ip) AS c) FROM Hit hit "
            + "WHERE hit.timestamp BETWEEN :start AND :end AND (hit.uri IN :uris OR :uris IS NULL) GROUP BY hit.app, hit.uri ORDER BY c DESC")
    Collection<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris);
}
