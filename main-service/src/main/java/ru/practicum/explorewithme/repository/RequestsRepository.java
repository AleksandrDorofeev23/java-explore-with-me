package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.model.Request;
import ru.practicum.explorewithme.model.User;

import java.util.List;

@Repository
public interface RequestsRepository extends JpaRepository<Request, Long> {

    List<Request> findByRequester(User user);

    boolean existsByRequester_idAndEvent_id(Long userId, Long eventId);

    List<Request> findByEvent_id(Long eventId);

    List<Request> findByIdIn(List<Long> requestIds);
}
