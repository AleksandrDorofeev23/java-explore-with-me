package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.NewUserRequest;
import ru.practicum.explorewithme.dto.UserDto;
import ru.practicum.explorewithme.exception.AlreadyExistsException;
import ru.practicum.explorewithme.mapper.UserMapper;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.repository.UsersRepository;
import ru.practicum.explorewithme.service.UsersService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final UserMapper userMapper;

    @Override
    public Collection<UserDto> getAll(List<Long> ids, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        if (ids == null) {
            return usersRepository.findAll(pageable).stream().map(userMapper::toDto).collect(Collectors.toList());
        } else {
            return usersRepository.findByIdIn(ids, pageable).stream().map(userMapper::toDto).collect(Collectors.toList());
        }
    }

    @Override
    public UserDto create(NewUserRequest newUserRequest) {
        if (usersRepository.existsByEmail(newUserRequest.getEmail())) {
            throw new AlreadyExistsException("Почта уже использована");
        }
        User user = userMapper.toModel(newUserRequest);
        return userMapper.toDto(usersRepository.save(user));
    }

    @Override
    public void delete(Long userId) {
        User user = usersRepository.findById(userId).get();
        usersRepository.delete(user);
    }
}
