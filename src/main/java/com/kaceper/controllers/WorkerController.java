package com.kaceper.controllers;

import com.kaceper.model.Worker;
import com.kaceper.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("pracownik")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @GetMapping("konto")
    public String konto(Model m) {
        String currentUserName = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }

        Worker worker = workerService.findByUname(currentUserName);

        m.addAttribute("worker", worker);

        return "pracownik/konto";
    }

    @GetMapping("pokoje")
    public String pokoje() {
        return "pracownik/pokoje";
    }

    @GetMapping("rezerwacje/pokoje")
    public String rezerwacjePokoje() {
        return "pracownik/rezerwacjePokoje";
    }

    @GetMapping("rezerwacje/wydarzenia")
    public String rezerwacjeSale() {
        return "pracownik/wydarzenia";
    }
}
