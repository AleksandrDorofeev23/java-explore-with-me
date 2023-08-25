package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.model.Category;

import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long> {

    boolean existsByName(String name);

    Optional<Category> findByName(String name);
}
