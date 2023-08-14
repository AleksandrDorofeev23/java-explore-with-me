package ru.practicum.explorewithme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsDto {
    private String app;
    private String uri;
    private long hits;
}
