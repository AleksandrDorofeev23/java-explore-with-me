package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.CategoryDto;
import ru.practicum.explorewithme.dto.NewCategoryDto;
import ru.practicum.explorewithme.exception.AlreadyExistsException;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.exception.RelatedException;
import ru.practicum.explorewithme.mapper.CategoryMapper;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.repository.CategoriesRepository;
import ru.practicum.explorewithme.repository.EventsRepository;
import ru.practicum.explorewithme.service.CategoriesService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository categoriesRepository;
    private final CategoryMapper categoryMapper;
    private final EventsRepository eventsRepository;

    @Override
    public CategoryDto create(NewCategoryDto newCategoryDto) {
        if (categoriesRepository.existsByName(newCategoryDto.getName())) {
            throw new AlreadyExistsException("Такая категория уже есть");
        }
        Category category = categoriesRepository.save(categoryMapper.toModel(newCategoryDto));
        return categoryMapper.toDto(category);
    }

    @Override
    public void delete(long catId) {
        Category category = categoryMapper.toModel(getById(catId));
        if (eventsRepository.existsByCategory(category)) {
            throw new RelatedException("Категория связана с событием");
        }
        categoriesRepository.delete(category);
    }

    @Override
    public CategoryDto update(long catId, NewCategoryDto newCategoryDto) {
        Category category = categoriesRepository.findById(catId).orElseThrow(()
                -> new NotFoundException("Такой категории нет"));
        if (categoriesRepository.existsByName(newCategoryDto.getName())) {
            if (categoriesRepository.findByName(newCategoryDto.getName()).get().getId() != catId)
                throw new AlreadyExistsException("Такая категория уже есть");
        }
        category.setName(newCategoryDto.getName());
        return categoryMapper.toDto(category);
    }

    @Override
    public Collection<CategoryDto> getAll(int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        return categoriesRepository.findAll(pageable).stream().map(categoryMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getById(long catId) {
        Category category = categoriesRepository.findById(catId).orElseThrow(()
                -> new NotFoundException("Такой категории нет"));
        return categoryMapper.toDto(category);
    }
}
