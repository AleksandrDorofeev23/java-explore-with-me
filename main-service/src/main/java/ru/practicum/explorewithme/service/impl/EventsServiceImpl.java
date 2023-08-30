package ru.practicum.explorewithme.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.StatisticsClient;
import ru.practicum.explorewithme.dto.*;
import ru.practicum.explorewithme.exception.EventValidException;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.exception.RequestValidException;
import ru.practicum.explorewithme.exception.TimeException;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.mapper.RequestsMapper;
import ru.practicum.explorewithme.model.*;
import ru.practicum.explorewithme.repository.*;
import ru.practicum.explorewithme.service.EventsService;
import ru.practicum.explorewithme.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.utils.Constants.FORMAT;

@Service
@RequiredArgsConstructor
public class EventsServiceImpl implements EventsService {

    private final EventsRepository eventsRepository;
    private final EventMapper eventMapper;
    private final UsersRepository usersRepository;
    private final CategoriesRepository categoriesRepository;
    private final LocationRepository locationRepository;
    private final RequestsRepository requestsRepository;
    private final RequestsMapper requestsMapper;
    private final StatisticsClient client;


    @Override
    public Collection<EventFullDto> getAllAdmin(List<Long> users, List<String> states, List<Long> categories,
                                                LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        List<State> statesEnum = new ArrayList<>();
        if (states != null) {
            for (String str : states) {
                if (str.equalsIgnoreCase(State.PUBLISHED.toString())) {
                    statesEnum.add(State.PUBLISHED);
                }
                if (str.equalsIgnoreCase(State.PENDING.toString())) {
                    statesEnum.add(State.PENDING);
                }
                if (str.equalsIgnoreCase(State.CANCELED.toString())) {
                    statesEnum.add(State.CANCELED);
                }
            }
        } else {
            statesEnum = null;
        }
        List<Event> events = eventsRepository.findAll(users, statesEnum, categories, rangeStart, rangeEnd, pageable);
        return events.stream().map(eventMapper::toFullDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto update(long eventId, UpdateEventAdminRequest dto) {
        Event event = eventsRepository.findById(eventId).orElseThrow(()
                -> new NotFoundException("Такого события нет"));
        if (dto.getEventDate() != null) {
            if (dto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
                throw new TimeException("Дата события должно быть не раньше 2 часов от текущего времени");
            }
            event.setEventDate(dto.getEventDate());
        }
        if (dto.getStateAction() != null) {
            if (dto.getStateAction().equals(StateAdmin.PUBLISH_EVENT.toString())) {
                if (event.getState().equals(State.PENDING)) {
                    event.setPublishedOn(LocalDateTime.now());
                    event.setState(State.PUBLISHED);
                } else {
                    throw new EventValidException("Нельзя опубликовать опубликованное или отмененное событие");
                }
            }
            if (dto.getStateAction().equals(StateAdmin.REJECT_EVENT.toString())) {
                if (event.getState().equals(State.PENDING)) {
                    event.setState(State.CANCELED);
                } else {
                    throw new EventValidException("Нельзя отменить опубликованное или отмененное событие");
                }
            }
        }
        if (dto.getAnnotation() != null) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategory() != null) {
            Category category = categoriesRepository.findById(dto.getCategory()).orElseThrow(()
                    -> new NotFoundException("Такой категории нет"));
            event.setCategory(category);
        }
        if (dto.getDescription() != null) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getLocation() != null) {
            if (event.getLocation().getLat() != dto.getLocation().getLat() ||
                    event.getLocation().getLon() != dto.getLocation().getLon()) {
                Location location = locationRepository.save(eventMapper.toLocationModel(dto.getLocation()));
                event.setLocation(location);
            }
        }
        if (dto.getPaid() != null) {
            event.setPaid(dto.getPaid());
        }
        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getRequestModeration() != null) {
            event.setRequestModeration(dto.getRequestModeration());
        }
        if (dto.getTitle() != null) {
            event.setTitle(dto.getTitle());
        }
        event = eventsRepository.save(event);
        return eventMapper.toFullDto(event);
    }

    @Override
    public Collection<EventShortDto> getAllByUser(long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        List<Event> events = eventsRepository.findByInitiator_id(userId, pageable);
        return events.stream().map(eventMapper::toShortDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto create(long userId, NewEventDto newEventDto) {
        Event event = eventMapper.toModel(newEventDto);
        if (newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new TimeException("Дата события должно быть не раньше 2 часов от текущего времени");
        }
        User user = usersRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Такого пользователя не существует"));
        Category category = categoriesRepository.findById(newEventDto.getCategory()).orElseThrow(()
                -> new NotFoundException("Такой категории не существует"));
        Location location = locationRepository.save(eventMapper.toLocationModel(newEventDto.getLocation()));
        event.setInitiator(user);
        event.setCategory(category);
        event.setLocation(location);
        event.setState(State.PENDING);
        event.setCreatedOn(LocalDateTime.now());
        return eventMapper.toFullDto(eventsRepository.save(event));
    }

    @Override
    public EventFullDto getByUserById(long userId, long eventId) {
        Event event = eventsRepository.findByIdAndInitiator_id(eventId, userId).orElseThrow(()
                -> new NotFoundException("Событие не найдено"));
        return eventMapper.toFullDto(event);
    }

    @Override
    public EventFullDto updateByUser(long userId, long eventId, UpdateEventUserRequest dto) {
        Event event = eventsRepository.findByIdAndInitiator_id(eventId, userId).orElseThrow(()
                -> new NotFoundException("Событие не найдено"));
        if (dto.getEventDate() != null) {
            if (dto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
                throw new TimeException("Дата события должно быть не раньше 2 часов от текущего времени");
            }
            event.setEventDate(dto.getEventDate());
        }
        if (!event.getState().equals(State.PENDING) && !event.getState().equals(State.CANCELED)) {
            throw new EventValidException("Нельзя изменить опубликованное событие");
        }
        if (dto.getStateAction() != null) {
            if (dto.getStateAction().equals(StateUser.SEND_TO_REVIEW.toString())) {
                event.setState(State.PENDING);
            }
            if (dto.getStateAction().equals(StateUser.CANCEL_REVIEW.toString())) {
                event.setState(State.CANCELED);
            }
        }
        if (dto.getAnnotation() != null) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategory() != null) {
            Category category = categoriesRepository.findById(dto.getCategory()).orElseThrow(()
                    -> new NotFoundException("Такой категории нет"));
            event.setCategory(category);
        }
        if (dto.getDescription() != null) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getLocation() != null) {
            if (event.getLocation().getLat() != dto.getLocation().getLat() ||
                    event.getLocation().getLon() != dto.getLocation().getLon()) {
                Location location = locationRepository.save(eventMapper.toLocationModel(dto.getLocation()));
                event.setLocation(location);
            }
        }
        if (dto.getPaid() != null) {
            event.setPaid(dto.getPaid());
        }
        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getRequestModeration() != null) {
            event.setRequestModeration(dto.getRequestModeration());
        }
        if (dto.getTitle() != null) {
            event.setTitle(dto.getTitle());
        }
        event = eventsRepository.save(event);
        return eventMapper.toFullDto(event);
    }

    @Override
    public Collection<ParticipationRequestDto> getRequestsByEventByUser(long userId, long eventId) {
        eventsRepository.findByIdAndInitiator_id(eventId, userId).orElseThrow(()
                -> new NotFoundException("Событие не найдено"));
        return requestsRepository.findByEvent_id(eventId).stream().map(requestsMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public EventRequestStatusUpdateResult updateRequests(long userId, long eventId,
                                                         EventRequestStatusUpdateRequest requestsStatuses) {
        Event event = eventsRepository.findByIdAndInitiator_id(eventId, userId).orElseThrow(()
                -> new NotFoundException("Событие не найдено"));
        List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
        List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();
        List<Request> requestList = requestsRepository.findByIdIn(requestsStatuses.getRequestIds());
        int available = (int) (event.getParticipantLimit() - event.getConfirmedRequests());
        for (Request request : requestList) {
            if (request.getStatus() != Status.PENDING) {
                throw new RequestValidException("Статус можно менять только у заявок в состоянии ожидания");
            }
        }
        if (requestsStatuses.getStatus().equals(Status.CONFIRMED.toString())) {
            if (event.getParticipantLimit() == event.getConfirmedRequests() && event.getParticipantLimit() > 0) {
                throw new RequestValidException("Достигнут лимит участников");
            }
            if (event.getParticipantLimit() == 0L || requestList.size() <= available) {
                for (Request request : requestList) {
                    request.setStatus(Status.CONFIRMED);
                }
                requestsRepository.saveAll(requestList);
                confirmedRequests = requestList.stream().map(requestsMapper::toDto).collect(Collectors.toList());
                event.setConfirmedRequests(event.getConfirmedRequests() + requestList.size());
                eventsRepository.save(event);
            } else {
                List<Request> subConfirmed = requestList.subList(0, available);
                for (Request request : subConfirmed) {
                    request.setStatus(Status.CONFIRMED);
                }
                List<Request> result = new ArrayList<>(subConfirmed);
                confirmedRequests = subConfirmed.stream().map(requestsMapper::toDto).collect(Collectors.toList());
                List<Request> subRejected = requestList.subList(available, requestList.size());
                for (Request request : subRejected) {
                    request.setStatus(Status.REJECTED);
                }
                result.addAll(subRejected);
                rejectedRequests = subRejected.stream().map(requestsMapper::toDto).collect(Collectors.toList());
                requestsRepository.saveAll(result);
                event.setConfirmedRequests(event.getConfirmedRequests() + subConfirmed.size());
                eventsRepository.save(event);
            }
        }
        if (requestsStatuses.getStatus().equals(Status.REJECTED.toString())) {
            for (Request request : requestList) {
                request.setStatus(Status.REJECTED);
            }
            requestsRepository.saveAll(requestList);
            rejectedRequests = requestList.stream().map(requestsMapper::toDto).collect(Collectors.toList());
        }
        return new EventRequestStatusUpdateResult(confirmedRequests, rejectedRequests);
    }

    @Override
    public Collection<EventShortDto> getAllPublic(String text, List<Long> categories, Boolean paid,
                                                  LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                  boolean onlyAvailable, String sort, int from, int size,
                                                  HttpServletRequest httpServletRequest) {
        Pageable pageable = PageRequest.of(from, size);
        List<Event> events;
        if (rangeStart != null && rangeEnd != null) {
            if (rangeEnd.isBefore(rangeStart)) {
                throw new TimeException("Дата начала должна быть раньше даты окончания");
            }
        }
        if (sort != null) {
            if (sort.equalsIgnoreCase("EVENT_DATE")) {
                pageable = PageRequest.of(from, size, Sort.by("eventDate").ascending());
            } else {
                pageable = PageRequest.of(from, size, Sort.by("views").ascending());
            }
        }
        events = eventsRepository.getPublicEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, pageable);
        Map<String, Long> mapHit = getMapHit(events.stream().map(a -> "/events/" + a.getId().toString()).toArray(String[]::new));
        List<EventShortDto> eventShortDtoList = events.stream().map(a -> {
            Long hits = mapHit.get("/events/" + a.getId().toString());
            EventShortDto eventShortDto = eventMapper.toShortDto(a);
            eventShortDto.setViews(Objects.requireNonNullElse(hits, 0L));
            return eventShortDto;
        }).collect(Collectors.toList());
        HitDto statDto = HitDto.builder().app("main-service")
                .uri(httpServletRequest.getRequestURI())
                .ip(httpServletRequest.getRemoteAddr())
                .timestamp(LocalDateTime.now().format(FORMAT))
                .build();
        client.create(statDto);
        return eventShortDtoList;
    }

    @Override
    public EventFullDto getById(long id, HttpServletRequest httpServletRequest) {
        Event event = eventsRepository.findByIdAndState(id, State.PUBLISHED).orElseThrow(()
                -> new NotFoundException("Такого события нет"));
        EventFullDto eventDto = eventMapper.toFullDto(event);
        Map<String, Long> mapHit = getMapHit(new String[]{"/events/" + event.getId().toString()});
        Long hits = mapHit.get("/events/" + event.getId().toString());
        eventDto.setViews(Objects.requireNonNullElse(hits, 0L));
        HitDto statDto = HitDto.builder().app("main-service")
                .uri(httpServletRequest.getRequestURI())
                .ip(httpServletRequest.getRemoteAddr())
                .timestamp(LocalDateTime.now().format(FORMAT))
                .build();
        client.create(statDto);
        return eventDto;
    }

    private Map<String, Long> getMapHit(String[] uris) {
        ResponseEntity<Object> response = client.getByParameters("2000-01-01 00:00:00",
                "3000-01-01 00:00:00", uris, true);
        Object body = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        List<StatsDto> statsDtoList = mapper.convertValue(body, new TypeReference<>() {
        });
        Map<String, Long> mapHit = new HashMap<>();
        for (StatsDto statsDto : statsDtoList) {
            mapHit.put(statsDto.getUri(), statsDto.getHits());
        }
        return mapHit;
    }


}
