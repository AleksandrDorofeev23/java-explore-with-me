package ru.practicum.explorewithme.controller.adminApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.service.CommentsService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/comments")
public class AdminComments {

    private final CommentsService commentsService;

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long commentId) {
        log.info("Получен запрос @DeleteMapping(/admin/comments/{}) ", commentId);
        commentsService.delete(commentId);
    }

    @PatchMapping("/{userId}")
    public void ban(@PathVariable Long userId, @RequestParam(defaultValue = "1") Integer days) {
        log.info("Получен запрос @PatchMapping(/admin/comments/{}) {}", userId, days);
        commentsService.ban(userId, days);
    }
}
