package com.belavus.sportsresult.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/first")
    public String firstPage() {
        return "welcome/welcomePage";
    }

}
