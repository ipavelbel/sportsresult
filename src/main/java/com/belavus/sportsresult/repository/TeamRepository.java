package com.belavus.sportsresult.repository;


import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Team;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Integer> {

    List<Team> findTeamsByEventsId(Integer EventId);

    @EntityGraph(attributePaths = "athletes")
    Optional<Team> findTeamWithAthletesById(int id);
}
