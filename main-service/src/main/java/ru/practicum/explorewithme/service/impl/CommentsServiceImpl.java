package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.CommentDto;
import ru.practicum.explorewithme.dto.NewCommentDto;
import ru.practicum.explorewithme.exception.*;
import ru.practicum.explorewithme.mapper.CommentMapper;
import ru.practicum.explorewithme.model.Comment;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.repository.CommentsRepository;
import ru.practicum.explorewithme.repository.EventsRepository;
import ru.practicum.explorewithme.repository.UsersRepository;
import ru.practicum.explorewithme.service.CommentsService;
import ru.practicum.explorewithme.utils.State;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;
    private final CommentMapper commentMapper;
    private final EventsRepository eventsRepository;
    private final UsersRepository usersRepository;

    @Override
    public void delete(Long commentId) {
        commentsRepository.findById(commentId).orElseThrow(()
                -> new NotFoundException("Такого комментария не существует"));
        commentsRepository.deleteById(commentId);
    }

    @Override
    public CommentDto create(Long userId, Long eventId, NewCommentDto newCommentDto) {
        User user = usersRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Такого пользователя не существует"));
        Event event = eventsRepository.findById(eventId).orElseThrow(()
                -> new NotFoundException("Такого события не существует"));
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new EventValidException("Комментарии можно оставлять только к опубликованным событиям");
        }
        if (user.getBanned() != null) {
            if (user.getBanned().isAfter(LocalDateTime.now())) {
                throw new BanException("Ползователь ограничен в написании комментариев до " + user.getBanned());
            }
        }
        Comment comment = commentMapper.toModel(newCommentDto);
        comment.setDate(LocalDateTime.now());
        comment.setAuthor(user);
        comment.setEvent(eventId);
        comment = commentsRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Override
    public CommentDto getById(Long commentId) {
        Comment comment = commentsRepository.findById(commentId).orElseThrow(()
                -> new NotFoundException("Такого комментария не существует"));
        return commentMapper.toDto(comment);
    }

    @Override
    public Collection<CommentDto> getAll(Long userId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        return commentsRepository.findByAuthor_id(userId, pageable).stream().map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto update(Long userId, Long commentId, NewCommentDto newCommentDto) {
        Comment comment = commentsRepository.findById(commentId).orElseThrow(()
                -> new NotFoundException("Такого комментария не существует"));
        if (!Objects.equals(comment.getAuthor().getId(), userId)) {
            throw new RelatedException("Комментарий не принадлежит пользователю");
        }
        User user = usersRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Такого пользователя не существует"));
        if (user.getBanned() != null) {
            if (user.getBanned().isAfter(LocalDateTime.now())) {
                throw new BanException("Ползователь ограничен в редактировании комментариев до " + user.getBanned());
            }
        }
        if (comment.getDate().plusHours(24).isBefore(LocalDateTime.now())) {
            throw new TimeException("Редактировать комментарии можно только в течение суток");
        }
        comment.setText(newCommentDto.getText());
        comment = commentsRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Override
    public void delete(Long userId, Long commentId) {
        Comment comment = commentsRepository.findById(commentId).orElseThrow(()
                -> new NotFoundException("Такого комментария не существует"));
        if (!Objects.equals(comment.getAuthor().getId(), userId)) {
            throw new RelatedException("Комментарий не принадлежит пользователю");
        }
        commentsRepository.deleteById(commentId);
    }

    @Override
    public Collection<CommentDto> getAllByEvent(Long eventId, Integer from, Integer size) {
        eventsRepository.findById(eventId).orElseThrow(()
                -> new NotFoundException("Такого события не существует"));
        Pageable pageable = PageRequest.of(from, size);
        return commentsRepository.findByEvent(eventId, pageable).stream().map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void ban(Long userId, Integer days) {
        User user = usersRepository.findById(userId).orElseThrow(()
                -> new NotFoundException("Такого пользователя не существует"));
        user.setBanned(LocalDateTime.now().plusDays(days));
        usersRepository.save(user);
    }
}
