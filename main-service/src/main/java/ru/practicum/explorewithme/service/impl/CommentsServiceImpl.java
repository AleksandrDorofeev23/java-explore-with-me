package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.mapper.CommentMapper;
import ru.practicum.explorewithme.repository.CommentsRepository;
import ru.practicum.explorewithme.service.CommentsService;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;
    private final CommentMapper commentMapper;
}
