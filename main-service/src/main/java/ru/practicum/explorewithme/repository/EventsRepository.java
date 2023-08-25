package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.utils.State;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventsRepository extends JpaRepository<Event, Long> {

    @Query("select a from Event as a join a.initiator as b join a.category as c " +
            "where (coalesce(:users, null) = null or b.id in :users) and (coalesce(:states, null) = null or a.state in :states) " +
            "and (coalesce(:categories, null) = null or c.id in :categories) and (coalesce(:rangeStart, null) = null or a.eventDate >= :rangeStart) " +
            "and (coalesce(:rangeEnd, null) = null or a.eventDate <= :rangeEnd)"
    )
    List<Event> findAll(List<Long> users,
                        List<State> states,
                        List<Long> categories,
                        LocalDateTime rangeStart,
                        LocalDateTime rangeEnd,
                        Pageable pageable);

    @Query("select a from Event as a join a.category as b " +
            "where a.state = ru.practicum.explorewithme.utils.State.PUBLISHED " +
            "and (Lower(a.annotation) like '%'||Lower(:text)||'%' or Lower(a.description) like '%'||Lower(:text)||'%' or :text = null) " +
            "and (coalesce(:categories, null) = null or b.id in :categories) " +
            "and (coalesce(:rangeStart, null) = null or a.eventDate >= :rangeStart) " +
            "and (coalesce(:rangeEnd, null) = null or a.eventDate <= :rangeEnd) " +
            "and ((coalesce(:rangeStart, null) = null and coalesce(:rangeEnd, null) = null and a.eventDate >= CURRENT_TIMESTAMP) " +
            "or coalesce(:rangeStart, null) <> null or coalesce(:rangeEnd, null) <> null) " +
            "and (:paid = null or a.paid = :paid) " +
            "and ((:onlyAvailable = true and a.participantLimit > a.confirmedRequests) " +
            "or (:onlyAvailable = false)) "
    )
    List<Event> getPublicEvents(String text,
                                List<Long> categories,
                                boolean paid,
                                LocalDateTime rangeStart,
                                LocalDateTime rangeEnd,
                                Boolean onlyAvailable,
                                Pageable pageable);

    List<Event> findByIdIn(List<Long> ids);

    boolean existsByCategory(Category category);

    List<Event> findByInitiator_id(long userId, Pageable page);

    Optional<Event> findByIdAndInitiator_id(long id, long initiatorId);

    Optional<Event> findByIdAndState(Long id, State state);

}
