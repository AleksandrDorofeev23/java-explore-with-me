package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.CategoryDto;
import ru.practicum.explorewithme.dto.NewCategoryDto;

import java.util.Collection;

public interface CategoriesService {

    CategoryDto create(NewCategoryDto newCategoryDto);

    void delete(long catId);

    CategoryDto update(long catId, NewCategoryDto newCategoryDto);

    Collection<CategoryDto> getAll(int from, int size);

    CategoryDto getById(long catId);
}
