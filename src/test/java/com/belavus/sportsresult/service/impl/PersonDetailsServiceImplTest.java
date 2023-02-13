package com.belavus.sportsresult.service.impl;

import com.belavus.sportsresult.model.Person;
import com.belavus.sportsresult.repository.PeopleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonDetailsServiceImplTest {

    @Mock
    PeopleRepository peopleRepository;

    @InjectMocks
    PersonDetailsServiceImpl personDetailsService;

    @Test
    void testLoadUserByUsername() {
        // Given
        Person person = new Person();
        person.setUsername("FirstName");
        when(peopleRepository.findByUsername(anyString())).thenReturn(Optional.of(person));

        // When
        UserDetails userDetails =personDetailsService.loadUserByUsername(anyString());

        // Then
        assertEquals("FirstName", userDetails.getUsername());
        verify(peopleRepository).findByUsername(anyString());

    }

    @Test
    void testLoadUserByUsernameUserNotFound(){
        // Given,When,Then
        assertThrows(UsernameNotFoundException.class, () -> personDetailsService.loadUserByUsername("Name"));

    }
}