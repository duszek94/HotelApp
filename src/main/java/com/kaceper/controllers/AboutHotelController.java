package com.kaceper.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AboutHotelController {

    @RequestMapping("/oHotelu")
    public String oHotelu() {
        return "oHotelu";
    }
}
