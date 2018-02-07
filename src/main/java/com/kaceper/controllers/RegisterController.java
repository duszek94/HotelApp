package com.kaceper.controllers;

import com.kaceper.model.Role;
import com.kaceper.model.Worker;
import com.kaceper.repository.WorkerRepository;
import com.kaceper.service.EmailService;
import com.kaceper.service.EmailServiceImpl;
import com.kaceper.service.WorkerService;
import com.kaceper.service.WorkerServiceImpl;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * Created by Kaceper on 2017-11-26.
 */


@Controller
public class RegisterController {

    @Autowired
    private WorkerService workerService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private WorkerRepository workerRepository;

    public RegisterController(WorkerServiceImpl workerService, EmailServiceImpl emailService){
        this.workerService = workerService;
        this.emailService = emailService;
    }



    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView processRegistrationForm(Model model,
                                                ModelAndView modelAndView,
                                                @Valid Worker worker,
                                                BindingResult bindingResult,
                                                @RequestParam List<String> rolesValues,
                                                @RequestParam Map requestParams,
                                                HttpServletRequest httpServletRequest){

        Worker workerWithEmailExists = workerService.findByEmail(worker.getEmail());

        if( workerWithEmailExists != null){
            model.addAttribute("errorMessage", "User o podanym adresie e-mail już istnieje");
            modelAndView.setViewName("admin/pracownicy");
            bindingResult.reject("email");
            System.out.println(worker.getEmail());
            System.out.println("Exists: " + workerWithEmailExists);
            return modelAndView;
        }

        Worker workerWithNameExists = workerService.findByUname(worker.getUsername());

        if( workerWithNameExists != null){
            model.addAttribute("errorMessage", "Pracownik o podanym loginie już istnieje, wybierz inny login");
            modelAndView.setViewName("admin/pracownicy");
            bindingResult.reject("username");
            System.out.println(worker.getUsername());
            System.out.println("Exists: " + workerWithNameExists);
            return modelAndView;
        }

        for(String role : rolesValues){
            System.out.println(role);
        }

        for (Object object : bindingResult.getAllErrors()) {
            if(object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;

                String message = messageSource.getMessage(fieldError, null);
                System.out.println(message);
            }
        }

        if(bindingResult.hasErrors()){
            modelAndView.setViewName("admin/pracownicy");
            return modelAndView;
        }else {
            worker.setEnabled(false);
            worker.setConfirmationToken(UUID.randomUUID().toString());

            Zxcvbn passwordCheck = new Zxcvbn();
            Strength strength = passwordCheck.measure(requestParams.get("password").toString());
            if(strength.getScore() < 2) {
                bindingResult.reject("password");
                modelAndView.addObject("errorMessage", "Hasło jest zbyt słabe, wybierz silniejsze");
                modelAndView.setViewName("admin/pracownicy");
                return modelAndView;
            }

            String pass = requestParams.get("password").toString();
            worker.setPassword(bCryptPasswordEncoder.encode(pass));

            if (worker == null) {
                modelAndView.addObject("errorMessage", "Uups!  Link aktywacyjny jest wadliwy");
            } else { // Token found
                modelAndView.addObject("confirmationToken", worker.getConfirmationToken());
            }

            model.addAttribute("successMessage", "Zarejestrowano pomyślnie, e-mail aktywacyjny został wysłany na adres " + worker.getEmail());
            modelAndView.setViewName("admin/pracownicy");
        }


        List<Role> roles = new ArrayList<>();
        for(String role : rolesValues){
            Role r = new Role(role);
            roles.add(r);
        }
        worker.setRoles(roles);

        workerRepository.save(worker);

        String appUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName();
        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo(worker.getEmail());
        registrationEmail.setSubject("Potwierdzenie rejestracji");
        registrationEmail.setText("Aby dokończyć rejestrację, kliknij w poniższy link: "
                + appUrl + ":8080/confirm?token=" + worker.getConfirmationToken());
        registrationEmail.setFrom("hotelwaltertorun@gmail.com");
        emailService.sendEmail(registrationEmail);

        return modelAndView;
    }


    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public ModelAndView processConfirmationForm(ModelAndView modelAndView, @RequestParam("token") String token){
        modelAndView.setViewName("confirm");
        Worker worker = workerService.findByConfirmationToken(token);
        boolean enabled = worker.isEnabled();

        if(enabled == true){
            modelAndView.addObject("errorMessage", "Konto zostało już aktywowane");
            modelAndView.setViewName("home");

            return modelAndView;

        }else if(enabled == false){
            modelAndView.addObject("login", worker.getUsername());

            if(worker == null){
                modelAndView.addObject("errorMessage", "Niepoprawny link potwierdzjący");
                modelAndView.setViewName("home");
                return modelAndView;
            } else {
                modelAndView.addObject("confirmationToken", worker.getConfirmationToken());
                worker.setEnabled(true);

                if(workerService.updateEnabled(worker)){
                    modelAndView.addObject("successMessage", "Konto jest teraz aktywne!");
                }else{
                    modelAndView.addObject("errorMessage", "Wystąpił błąd podczas aktywacji konta");
                }

                return modelAndView;
            }
        }

        return modelAndView;
    }
}
