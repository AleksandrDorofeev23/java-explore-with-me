package ru.practicum.explorewithme.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.dto.LocationDto;
import ru.practicum.explorewithme.dto.NewEventDto;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.Location;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;

    public Event toModel(NewEventDto eventDto) {
        if (eventDto == null) {
            return null;
        }
        Event event = new Event();
        event.setAnnotation(eventDto.getAnnotation());
        event.setDescription(eventDto.getDescription());
        event.setEventDate(eventDto.getEventDate());
        event.setLocation(toLocationModel(eventDto.getLocation()));
        event.setPaid(eventDto.getPaid());
        event.setParticipantLimit(eventDto.getParticipantLimit());
        event.setRequestModeration(eventDto.getRequestModeration());
        event.setTitle(eventDto.getTitle());
        return event;
    }

    public EventFullDto toFullDto(Event event) {
        if (event == null) {
            return null;
        }
        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setId(event.getId());
        eventFullDto.setConfirmedRequests(event.getConfirmedRequests());
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setCategory(categoryMapper.toDto(event.getCategory()));
        eventFullDto.setCreatedOn(event.getCreatedOn());
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setEventDate(event.getEventDate());
        eventFullDto.setInitiator(userMapper.toShortDto(event.getInitiator()));
        eventFullDto.setLocation(toLocationDto(event.getLocation()));
        eventFullDto.setPaid(event.getPaid());
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        eventFullDto.setPublishedOn(event.getPublishedOn());
        eventFullDto.setRequestModeration(event.getRequestModeration());
        eventFullDto.setState(event.getState().toString());
        eventFullDto.setTitle(event.getTitle());
        return eventFullDto;
    }

    public EventShortDto toShortDto(Event event) {
        if (event == null) {
            return null;
        }
        EventShortDto eventShortDto = new EventShortDto();
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setConfirmedRequests(event.getConfirmedRequests());
        eventShortDto.setCategory(categoryMapper.toDto(event.getCategory()));
        eventShortDto.setEventDate(event.getEventDate());
        eventShortDto.setId(event.getId());
        eventShortDto.setInitiator(userMapper.toShortDto(event.getInitiator()));
        eventShortDto.setPaid(event.getPaid());
        eventShortDto.setTitle(event.getTitle());
        return eventShortDto;
    }

    public Location toLocationModel(LocationDto locationDto) {
        if (locationDto == null) {
            return null;
        }
        Location location = new Location();
        location.setLat(locationDto.getLat());
        location.setLon(locationDto.getLon());
        return location;
    }

    private LocationDto toLocationDto(Location location) {
        if (location == null) {
            return null;
        }
        LocationDto locationDto = new LocationDto();
        locationDto.setLat(location.getLat());
        locationDto.setLon(location.getLon());
        return locationDto;
    }
}
