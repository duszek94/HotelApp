package com.kaceper.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Kaceper on 2017-11-08.
 */

@Controller
@RequestMapping("gastronomia")
public class GastroController {

    @GetMapping("restauracja")
    public String restauracja() {
        return "gastronomia/restauracja";
    }

    @GetMapping("bar")
    public String bar() {
        return "gastronomia/bar";
    }

    @GetMapping("sniadania")
    public String śniadania() {
        return "gastronomia/sniadania";
    }

}

