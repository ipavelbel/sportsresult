package com.belavus.sportsresult.repository;


import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {

    Event findEventById(Integer id);

    @EntityGraph(attributePaths = "athletes")
    Optional<Event> findEventWithAthletesById(int id);

    @EntityGraph(attributePaths = "teams")
    Optional<Event> findEventWithTeamsById(int id);

}
