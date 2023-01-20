package com.belavus.sportsresult.repository;


import com.belavus.sportsresult.model.Athlete;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AthleteRepository extends JpaRepository<Athlete, Integer> {


    @EntityGraph(attributePaths = "teams")
    Optional<Athlete> findAthleteWithTeamsById(int id);
//    @Modifying
//    @Query( value = "insert into teams_athletes (team_id, athletes_id) values (:athleteId, teamId)")
//public void updateAthleteIdAndTeamId(Integer athleteId,Integer teamId);

}
