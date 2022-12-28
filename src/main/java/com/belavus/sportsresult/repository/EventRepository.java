package com.belavus.sportsresult.repository;



import com.belavus.sportsresult.model.Event;
import com.belavus.sportsresult.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

////    @Query(value = "select teams.* from teams where event_id = :id", nativeQuery = true)
//    @Query(value = "select * from teams where event_id = 1", nativeQuery = true)
//    List<Team> getTeamsByEventId(Integer id);
}
