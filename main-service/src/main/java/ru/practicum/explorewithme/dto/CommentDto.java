package ru.practicum.explorewithme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static ru.practicum.explorewithme.utils.Constants.DATA_TIME_FORMAT;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATA_TIME_FORMAT)
    private LocalDateTime date;
    private UserShortDto author;
    private Long event;
}
