package ru.practicum.explorewithme.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.*;
import ru.practicum.explorewithme.model.User;

@Component
public class UserMapper {

    public User toModel(NewUserRequest newUserRequest) {
        if (newUserRequest == null) {
            return null;
        }
        User user = new User();
        user.setEmail(newUserRequest.getEmail());
        user.setName(newUserRequest.getName());
        return user;
    }

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        return userDto;
    }

    public UserShortDto toShortDto(User user) {
        if (user == null) {
            return null;
        }
        UserShortDto userDto = new UserShortDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        return userDto;
    }
}
