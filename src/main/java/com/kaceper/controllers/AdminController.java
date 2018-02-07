package com.kaceper.controllers;

import com.kaceper.model.Worker;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {

    @GetMapping("pracownicy")
    public String pracownicy(Model model, Worker worker) {
        model.addAttribute(worker);
        return "admin/pracownicy";
    }

    @GetMapping("pokoje")
    public String pokoje() {
        return "admin/pokoje";
    }
}
