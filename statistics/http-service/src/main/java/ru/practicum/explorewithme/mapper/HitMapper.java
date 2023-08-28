package ru.practicum.explorewithme.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.HitDto;
import ru.practicum.explorewithme.model.Hit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.practicum.explorewithme.utils.Constants.DATA_TIME_FORMAT;

@Component
public class HitMapper {

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern(DATA_TIME_FORMAT);

    public Hit toHit(HitDto hitDto) {
        if (hitDto == null) {
            return null;
        }
        Hit hit = new Hit();
        hit.setIp(hitDto.getIp());
        hit.setApp(hitDto.getApp());
        hit.setUri(hitDto.getUri());
        hit.setTimestamp(LocalDateTime.parse(hitDto.getTimestamp(), format));
        return hit;
    }
}
