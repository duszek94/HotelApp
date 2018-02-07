package com.kaceper.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Kaceper on 2017-11-11.
 */
@Controller
@RequestMapping("dlafirm")
public class CompaniesController {

    @GetMapping("konferencje")
    public String konferencje() {
        return "dlafirm/konferencje";
    }
}
