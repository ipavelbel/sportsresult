package com.belavus.sportsresult.repository;


import com.belavus.sportsresult.model.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AthleteRepository extends JpaRepository<Athlete, Integer> {
}
