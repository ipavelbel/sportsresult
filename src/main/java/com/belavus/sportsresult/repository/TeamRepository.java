package com.belavus.sportsresult.repository;


import com.belavus.sportsresult.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
