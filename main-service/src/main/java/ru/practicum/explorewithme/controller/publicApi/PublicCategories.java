package ru.practicum.explorewithme.controller.publicApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.CategoryDto;
import ru.practicum.explorewithme.service.CategoriesService;

import javax.validation.constraints.Min;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/categories")
public class PublicCategories {

    private final CategoriesService categoriesService;

    @GetMapping
    public Collection<CategoryDto> getAll(@RequestParam(defaultValue = "0") @Min(0) int from,
                                          @RequestParam(defaultValue = "10") @Min(1) int size) {
        log.info("Получен запрос @GetMapping(/categories) {} {}", from, size);
        return categoriesService.getAll(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getById(@PathVariable long catId) {
        log.info("Получен запрос @GetMapping(/categories/{})", catId);
        return categoriesService.getById(catId);
    }
}
