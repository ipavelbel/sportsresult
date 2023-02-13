package com.belavus.sportsresult.service.impl;

import com.belavus.sportsresult.model.Person;
import com.belavus.sportsresult.repository.PeopleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {

    @Mock
    PeopleRepository peopleRepository;

    @InjectMocks
    RegistrationServiceImpl registrationService;

    @Mock
    PasswordEncoder passwordEncoder;


    @Test
    void testRegister() {
        // Given
        Person person = new Person();
        person.setUsername("name");
        person.setYearOfBirth(1988);
        person.setPassword("11111");
        passwordEncoder.encode((person.getPassword()));
        when(peopleRepository.save(person)).thenReturn(person);

        // When
        registrationService.register(person);

        // Then
        ArgumentCaptor<Person> argumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(peopleRepository).save(argumentCaptor.capture());
        Person captureValue = argumentCaptor.getValue();
        assertEquals("ROLE_USER", captureValue.getRole());

    }
}