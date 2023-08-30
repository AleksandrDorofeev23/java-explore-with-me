package ru.practicum.explorewithme.controller.publicApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.service.CommentsService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comments")
public class PublicComments {

    private final CommentsService commentsService;
}
