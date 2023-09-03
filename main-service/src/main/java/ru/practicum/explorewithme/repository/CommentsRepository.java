package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.model.Comment;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByAuthor_id(Long userId, Pageable pageable);

    List<Comment> findByEvent(Long eventId, Pageable pageable);
}
