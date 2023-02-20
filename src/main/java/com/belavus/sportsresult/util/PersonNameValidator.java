package com.belavus.sportsresult.util;

import com.belavus.sportsresult.model.Person;
import com.belavus.sportsresult.service.impl.PersonDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonNameValidator implements Validator {

    private final PersonDetailsServiceImpl personDetailsService;

    @Autowired
    public PersonNameValidator(PersonDetailsServiceImpl personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        try {
            personDetailsService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return; // OK, user not found
        }

        errors.rejectValue("username", "", "A person with this username already exists");
    }
}
