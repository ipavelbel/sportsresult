package com.belavus.sportsresult.repository;

import com.belavus.sportsresult.model.Athlete;
import com.belavus.sportsresult.model.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class AthleteRepositoryTest {

    @Autowired
    AthleteRepository athleteRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void testAddAthleteInTeam() {
        Team team = new Team("team", "newcoah");
        Athlete athlete = new Athlete("hame", "surname", 44);
        Athlete athlete2 = new Athlete("hame1", "surname1", 23);

        team.setAthletes(new LinkedHashSet<>(List.of(athlete, athlete2)));
        athlete.setTeams(new LinkedHashSet<>(Collections.singletonList(team)));
        athleteRepository.save(athlete);

    }
    @Test
    public void testChangeIdAthleteInTeam() {

        Team team = new Team("111", "cccccc");
        Athlete athlete = new Athlete("nnnn", "ksjdfl", 44);
        Athlete athlete2 = new Athlete("sdaf", "121414", 412214);

        athlete.addTeam(team);
        athlete2.addTeam(team);
        athleteRepository.save(athlete);
        athleteRepository.save(athlete2);



    }



}