package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.dto.HitDto;
import ru.practicum.explorewithme.model.Hit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HitMapper {

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
