package ru.practicum.explorewithme.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.model.Request;

@Component
public class RequestsMapper {

    public ParticipationRequestDto toDto(Request request) {
        if (request == null) {
            return null;
        }
        ParticipationRequestDto dto = new ParticipationRequestDto();
        dto.setRequester(request.getRequester().getId());
        dto.setId(request.getId());
        dto.setEvent(request.getEvent().getId());
        dto.setCreated(request.getCreated());
        dto.setStatus(request.getStatus().toString());
        return dto;
    }
}
