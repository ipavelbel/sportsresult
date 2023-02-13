package com.belavus.sportsresult.controller;

import com.belavus.sportsresult.model.Person;
import com.belavus.sportsresult.service.RegistrationService;
import com.belavus.sportsresult.service.impl.RegistrationServiceImpl;
import com.belavus.sportsresult.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {


    private final RegistrationService registrationService;
    private final PersonValidator personValidator;

    @Autowired // TODO: n.kvetko: unnecessary annotation and code formatting
    public AuthController(RegistrationService registrationService, PersonValidator personValidator) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
    }


    @GetMapping("/login")
    public String loginPage(){ // TODO: n.kvetko: code formatting
        return "welcome/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) { // TODO: n.kvetko: unused parameters (?)
        return "welcome/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) { // TODO: n.kvetko:  unnecessary line break;
                                                                     // TODO: n.kvetko line length is usually 120,but it depends on the project
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()){ // TODO: n.kvetko add braces to "if" statement
            return "/welcome/registration";
        }

        registrationService.register(person);

        return "redirect:/welcome/login";
    }
}
