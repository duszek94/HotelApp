package com.kaceper.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Kaceper on 2017-11-04.
 */

@Controller
@RequestMapping("hotel")
public class HotelController {

    @GetMapping("oHotelu")
    public String oHotelu() {
        return "hotel/oHotelu";
    }

    @GetMapping("atrakcje")
    public String atrakcje() { return "hotel/atrakcje"; }

    @GetMapping("historia")
    public String historia() {
        return "hotel/historia";
    }

    @GetMapping("kontakt")
    public String kontakt() {
        return "hotel/kontakt";
    }
}