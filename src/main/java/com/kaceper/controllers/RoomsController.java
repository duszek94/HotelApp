package com.kaceper.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Kaceper on 2017-11-04.
 */

@Controller
@RequestMapping("pokoje")
public class RoomsController {

    @GetMapping("2os")
    public String dwa() {
        return "pokoje/2os";
    }

    @GetMapping("3os")
    public String trzy() {
        return "pokoje/3os";
    }

    @GetMapping("ap")
    public String apartament() {
        return "pokoje/apartament";
    }

    @GetMapping("apDL")
    public String apartamentDelux() {
        return "pokoje/apartamentDeLux";
    }
}