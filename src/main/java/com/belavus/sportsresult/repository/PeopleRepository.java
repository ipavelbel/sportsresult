package com.belavus.sportsresult.repository;

import com.belavus.sportsresult.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByUsername(String username);

}
