package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.CommentDto;
import ru.practicum.explorewithme.dto.NewCommentDto;

import java.util.Collection;

public interface CommentsService {

    void delete(Long commentId);

    CommentDto create(Long userId, Long eventId, NewCommentDto newCommentDto);

    CommentDto getById(Long commentId);

    Collection<CommentDto> getAll(Long userId, Integer from, Integer size);

    CommentDto update(Long userId, Long commentId, NewCommentDto newCommentDto);

    void delete(Long userId, Long commentId);

    Collection<CommentDto> getAllByEvent(Long eventId, Integer from, Integer size);

    void ban(Long userId, Integer days);
}
