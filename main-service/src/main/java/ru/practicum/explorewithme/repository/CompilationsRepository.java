package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.model.Compilation;

import java.util.List;

@Repository
public interface CompilationsRepository extends JpaRepository<Compilation, Long> {

    List<Compilation> findByPinned(Boolean pinned, Pageable page);
}
