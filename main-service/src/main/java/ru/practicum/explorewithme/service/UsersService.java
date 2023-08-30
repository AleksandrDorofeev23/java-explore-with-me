package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.NewUserRequest;
import ru.practicum.explorewithme.dto.UserDto;

import java.util.Collection;
import java.util.List;

public interface UsersService {

    Collection<UserDto> getAll(List<Long> ids, Integer from, Integer size);

    UserDto create(NewUserRequest newUserRequest);

    void delete(Long userId);
}
