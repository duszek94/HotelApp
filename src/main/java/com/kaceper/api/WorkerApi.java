package com.kaceper.api;

import com.kaceper.dto.WorkerDto;
import com.kaceper.dto.input.ListDto;
import com.kaceper.model.Worker;
import com.kaceper.repository.WorkerRepository;
import com.kaceper.service.WorkerService;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/worker")
public class WorkerApi {

    @Autowired
    private WorkerService workerService;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/all")
    public ListDto<WorkerDto> getAllRooms() {
        return ListDto.make( () -> workerService.getAllWorkers() );
    }

    @PostMapping(value = "/change/password", consumes = "application/text")
    public String changePassword(
            @RequestBody @Valid String password,
            Model model
    ) throws Exception{
        String message = "";
        String username = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }
        Worker worker = workerService.findByUname(username);
        model.addAttribute("worker", worker);

        if(bCryptPasswordEncoder.matches(password, worker.getPassword())){
            message = "same";
            return message;
        }
        Zxcvbn passwordCheck = new Zxcvbn();
        Strength strength = passwordCheck.measure(password);
        if(strength.getScore() < 2) {
            message = "weak";
            return message;
        }else if(strength.getScore() >= 2){
            String p = bCryptPasswordEncoder.encode(password);
            if(workerService.updatePassword(worker, p) == true){
                message = "ok";
                return message;
            }
        }
        return message;
    }


    @PostMapping(value = "/activate", consumes = "application/json")
    public String setActive(
            @RequestBody int id
    ){
        String message = "";
        Worker w = workerRepository.findOne(id);
        if(w.isEnabled() == true){
            return "Konto zostało już prędzej aktywowane";
        }

        if(w != null){
            if(workerService.updateEnabled(w)){
                message = "Konto zostało aktywowane";
            }else{
                message = "Nie można było aktywować konta";
            }
        }

        return message;
    }


    @PostMapping(value = "/update", consumes = "application/json")
    public String update(
            @RequestBody WorkerDto dto
    ){
        String message = "";

        if(workerService.updateWorker(dto)){
            message = "Uaktualniono dane użytkownika";
        }else{
            message = "Nie można było uaktualnić konta";
        }

        return message;
    }

    @PostMapping(value = "/delete", consumes = "application/json")
    public String deleteWorkers(
            @RequestBody List<Integer> ids
    ){
        String msg = "";

        for(int id : ids){
            if(workerService.deleteWorker(id)){
                msg = "Usunięto dane";
            }
            else msg = "Wystąpił błąd podczas usuwania danych";
        }

        return msg;
    }
}
