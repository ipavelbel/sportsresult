package com.belavus.sportsresult.model;

import com.belavus.sportsresult.repository.TeamRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    TeamRepository teamRepository;

    @Test
    void addEvents() {
        Event event = new Event("Frendly", "Minsk");
        Team team1 = new Team();
        team1.addEvents(event);
        teamRepository.save(team1);
    }

    @Test
    void removeEvents() {
    }
}