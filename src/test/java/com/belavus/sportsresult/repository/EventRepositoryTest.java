package com.belavus.sportsresult.repository;

import com.belavus.sportsresult.model.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void findEventById() {
    }

    @Test
    void testAddAthleteToEvent() {


    }

}