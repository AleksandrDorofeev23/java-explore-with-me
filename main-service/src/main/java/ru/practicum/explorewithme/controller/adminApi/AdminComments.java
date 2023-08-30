package ru.practicum.explorewithme.controller.adminApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.service.CommentsService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/comments")
public class AdminComments {

    private final CommentsService commentsService;
}
