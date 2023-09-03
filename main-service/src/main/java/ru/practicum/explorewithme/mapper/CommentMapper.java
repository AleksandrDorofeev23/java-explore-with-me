package ru.practicum.explorewithme.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.CommentDto;
import ru.practicum.explorewithme.dto.NewCommentDto;
import ru.practicum.explorewithme.model.Comment;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserMapper userMapper;

    public Comment toModel(NewCommentDto newCommentDto) {
        if (newCommentDto == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setText(newCommentDto.getText());
        return comment;
    }

    public CommentDto toDto(Comment comment) {
        if (comment == null) {
            return null;
        }
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setDate(comment.getDate());
        commentDto.setAuthor(userMapper.toShortDto(comment.getAuthor()));
        commentDto.setEvent(comment.getEvent());
        commentDto.setText(comment.getText());
        return commentDto;
    }
}
