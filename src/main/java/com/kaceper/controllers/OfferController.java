package com.kaceper.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Kaceper on 2017-11-08.
 */

@Controller
@RequestMapping("oferta")
public class OfferController {

    @GetMapping("uroczystosci")
    public String restauracja() {
        return "oferta/uroczystosci";
    }

    @GetMapping("specjalne")
    public String bar() {
        return "oferta/specjalne";
    }

}

