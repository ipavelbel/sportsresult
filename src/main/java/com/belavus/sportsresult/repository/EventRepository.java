package com.belavus.sportsresult.repository;


import com.belavus.sportsresult.model.Event;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {


    @EntityGraph(attributePaths = "athletes")
    Optional<Event> findEventWithAthletesById(Integer id);

    @EntityGraph(attributePaths = "teams")
    Optional<Event> findEventWithTeamsById(Integer id);

}
