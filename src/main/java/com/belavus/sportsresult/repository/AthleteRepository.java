package com.belavus.sportsresult.repository;


import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AthleteRepository extends JpaRepository<Athlete, Integer> {

}
