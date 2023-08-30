package ru.practicum.explorewithme.controller.priavteApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.service.CommentsService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users/{userId}/comments")
public class PrivateComments {

    private final CommentsService commentsService;
}
