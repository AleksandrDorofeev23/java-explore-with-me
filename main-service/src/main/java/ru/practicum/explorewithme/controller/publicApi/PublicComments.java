package ru.practicum.explorewithme.controller.publicApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.CommentDto;
import ru.practicum.explorewithme.service.CommentsService;

import javax.validation.constraints.Min;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comments")
public class PublicComments {

    private final CommentsService commentsService;

    @GetMapping("/{commentId}")
    public CommentDto getById(@PathVariable Long commentId) {
        log.info("Получен запрос @GetMapping(/comments/{})", commentId);
        return commentsService.getById(commentId);
    }

    @GetMapping("/all/{eventId}")
    public Collection<CommentDto> getAllByEvent(@PathVariable Long eventId,
                                                @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                                @RequestParam(defaultValue = "10") @Min(1) Integer size) {
        log.info("Получен запрос @GetMapping(/comments/{}) {} {}", eventId, from, size);
        return commentsService.getAllByEvent(eventId, from, size);
    }
}
