package com.belavus.sportsresult.repository;


import com.belavus.sportsresult.model.Team;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Integer> {


    @EntityGraph(attributePaths = "athletes")
    Optional<Team> findTeamWithAthletesById(Integer id);

    @EntityGraph(attributePaths = "events")
    Optional<Team> findTeamWithEventsById(Integer id);
}
