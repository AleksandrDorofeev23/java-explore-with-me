package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventsService {

    Collection<EventFullDto> getAllAdmin(List<Long> users, List<String> states, List<Long> categories,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    EventFullDto update(Long eventId, UpdateEventAdminRequest updateEventAdminRequest);

    Collection<EventShortDto> getAllByUser(Long userId, Integer from, Integer size);

    EventFullDto create(Long userId, NewEventDto newEventDto);

    EventFullDto getByUserById(Long userId, Long eventId);

    EventFullDto updateByUser(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest);

    Collection<ParticipationRequestDto> getRequestsByEventByUser(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateRequests(Long userId, Long eventId,
                                                          EventRequestStatusUpdateRequest requestsStatuses);

    Collection<EventShortDto> getAllPublic(String text, List<Long> categories, Boolean paid,
                                           LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                           Boolean onlyAvailable, String sort, Integer from, Integer size,
                                           HttpServletRequest httpServletRequest);

    EventFullDto getById(Long id, HttpServletRequest httpServletRequest);
}
