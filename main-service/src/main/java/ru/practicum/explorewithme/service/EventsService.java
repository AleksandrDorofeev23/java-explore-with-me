package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventsService {

    Collection<EventFullDto> getAllAdmin(List<Long> users, List<String> states, List<Long> categories,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);

    EventFullDto update(long eventId, UpdateEventAdminRequest updateEventAdminRequest);

    Collection<EventShortDto> getAllByUser(long userId, int from, int size);

    EventFullDto create(long userId, NewEventDto newEventDto);

    EventFullDto getByUserById(long userId, long eventId);

    EventFullDto updateByUser(long userId, long eventId, UpdateEventUserRequest updateEventUserRequest);

    Collection<ParticipationRequestDto> getRequestsByEventByUser(long userId, long eventId);

    EventRequestStatusUpdateResult updateRequests(long userId, long eventId,
                                                          EventRequestStatusUpdateRequest requestsStatuses);

    Collection<EventShortDto> getAllPublic(String text, List<Long> categories, boolean paid,
                                           LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                           boolean onlyAvailable, String sort, int from, int size,
                                           HttpServletRequest httpServletRequest);

    EventFullDto getById(long id, HttpServletRequest httpServletRequest);
}
