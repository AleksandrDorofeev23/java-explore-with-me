package ru.practicum.explorewithme.controller.priavteApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.*;
import ru.practicum.explorewithme.service.CommentsService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users/{userId}/comments")
public class PrivateComments {

    private final CommentsService commentsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(@PathVariable Long userId, @RequestParam Long eventId,
                             @RequestBody @Valid NewCommentDto newCommentDto) {
        log.info("Получен запрос @PostMapping(/users/{}/comments?eventId={})" + newCommentDto.toString(), userId, eventId);
        return commentsService.create(userId, eventId, newCommentDto);
    }

    @GetMapping
    public Collection<CommentDto> getAll(@PathVariable Long userId,
                                         @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                         @RequestParam(defaultValue = "10") @Min(1) Integer size) {
        log.info("Получен запрос @GetMapping(/users/{}/comments) {} {}", userId, from, size);
        return commentsService.getAll(userId, from, size);
    }

    @PatchMapping("/{commentId}")
    public CommentDto update(@PathVariable Long userId,
                             @PathVariable Long commentId,
                             @RequestBody @Valid NewCommentDto newCommentDto) {
        log.info("Получен запрос @PatchMapping(/users/{}/comments/{})" + newCommentDto.toString(), userId, commentId);
        return commentsService.update(userId, commentId, newCommentDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId, @PathVariable Long commentId) {
        log.info("Получен запрос @DeleteMapping(/users/{}/comments/{}) ", userId, commentId);
        commentsService.delete(userId, commentId);
    }
}
