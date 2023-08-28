package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.exception.RequestValidException;
import ru.practicum.explorewithme.mapper.RequestsMapper;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.Request;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.repository.EventsRepository;
import ru.practicum.explorewithme.repository.RequestsRepository;
import ru.practicum.explorewithme.repository.UsersRepository;
import ru.practicum.explorewithme.service.RequestsService;
import ru.practicum.explorewithme.utils.State;
import ru.practicum.explorewithme.utils.Status;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestsServiceImpl implements RequestsService {

    private final RequestsRepository requestsRepository;
    private final RequestsMapper requestsMapper;
    private final UsersRepository usersRepository;
    private final EventsRepository eventsRepository;

    @Override
    public Collection<ParticipationRequestDto> getById(long userId) {
        User user = usersRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Такого пользователя не существует"));
        return requestsRepository.findByRequester(user).stream().map(requestsMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto create(long userId, long eventId) {
        User user = usersRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Такого пользователя не существует"));
        Event event = eventsRepository.findById(eventId).orElseThrow(()
                -> new NotFoundException("Такого события не существует"));
        if (requestsRepository.existsByRequester_idAndEvent_id(userId, eventId)) {
            throw new RequestValidException("Такой запрос уже существует");
        }
        if (event.getInitiator().getId() == userId) {
            throw new RequestValidException("Нельзя участвовать в своем событии");
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new RequestValidException("Событие еще не опубликовано");
        }
        if (event.getParticipantLimit() <= event.getConfirmedRequests() && event.getParticipantLimit() != 0) {
            throw new RequestValidException("Достиг лимит запросов на участие");
        }
        var status = Status.PENDING;
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0L) {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventsRepository.save(event);
            status = Status.CONFIRMED;
        }
        Request request = new Request();
        request.setEvent(event);
        request.setStatus(status);
        request.setRequester(user);
        request.setCreated(LocalDateTime.now());
        return requestsMapper.toDto(requestsRepository.save(request));
    }

    @Override
    public ParticipationRequestDto update(long userId, long requestId) {
        usersRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Такого пользователя не существует"));
        Request request = requestsRepository.findById(requestId).orElseThrow(()
                -> new NotFoundException("Такого запроса не существует"));
        request.setStatus(Status.CANCELED);
        return requestsMapper.toDto(requestsRepository.save(request));
    }
}
