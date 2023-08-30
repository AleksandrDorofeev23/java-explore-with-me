package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.CategoryDto;
import ru.practicum.explorewithme.dto.NewCategoryDto;

import java.util.Collection;

public interface CategoriesService {

    CategoryDto create(NewCategoryDto newCategoryDto);

    void delete(Long catId);

    CategoryDto update(Long catId, NewCategoryDto newCategoryDto);

    Collection<CategoryDto> getAll(Integer from, Integer size);

    CategoryDto getById(Long catId);
}
