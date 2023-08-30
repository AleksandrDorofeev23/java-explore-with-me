package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.model.Comment;

public interface CommentsRepository extends JpaRepository<Comment, Long> {
}
