package com.kaceper.api;


import com.kaceper.dto.ReservationDto;
import com.kaceper.dto.UserDto;
import com.kaceper.dto.input.ListDto;
import com.kaceper.service.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationApi {

    private UserDto userDto;

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @Autowired
    ReservationService reservationService;


    @PostMapping(value = "/test", consumes = "application/json")
    public String test(
            @RequestBody String s) {

        return s;
    }

    @PostMapping(value = "/create/user", consumes = "application/json")
    public int createUser(
            @RequestBody UserDto dto) {

        userDto = UserDto.make(userService.createUser(dto));

        int userId = userDto.getUserId();

        return userId;
    }

    @PostMapping(value = "/update/user", consumes = "application/json")
    public UserDto updateUser(
            @RequestBody UserDto dto) {

        userDto = UserDto.make(userService.updateUser(dto));

        return userDto;
    }

    @PostMapping(value = "/create/reservation", consumes = "application/json")
    public ReservationDto createReservation(
            @RequestBody ReservationDto dto) {

        String paymentMethod = "";
        if(dto.getPaymentMethod().equals("cash")){
            paymentMethod = "\n\nKwotę: " + dto.getPrice() + " zł. należy przelać na dane:" +
                            "\nHotel Walter" +
                            "\nnr konta: 0000 1111 2222 3333 4444 5555";
        }else{
            paymentMethod = "\n\nKwota do zapłaty: " + dto.getPrice();
        }


        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(userDto.getEmail());
        email.setSubject("Potwierdzenie rezerwacji");
        email.setText(
                    "Dane użytkownika: " + userDto.getFirstname() + " " + userDto.getLastname() +
                        " \nEmail: " + userDto.getEmail() + ", telefon: " + userDto.getPhone() +
                            "\nOd: " + dto.getDateFrom() + " do: " + dto.getDateTo() +
                                "\nMetoda płatności: " + dto.getPaymentMethod() +
                                    "\nSzczegóły: " + dto.getDetails() + paymentMethod);

        email.setFrom("hotelwaltertorun@gmail.com");
        emailService.sendEmail(email);


        return ReservationDto.make(reservationService.createReservation(dto));
    }

    @PostMapping(value = "/update/reservation", consumes = "application/json")
    public ReservationDto updateReservation(
            @RequestBody ReservationDto dto) {
        ReservationDto reservationDto = ReservationDto.make(reservationService.updateReservation(dto));

        return reservationDto;
    }


    @GetMapping("/all")
    public ListDto< HashMap<String, String> > getAll(){
        return ListDto.make( () -> reservationService.getAll());
    }

    @GetMapping("/one/{reservationId}")
    public ReservationDto getOne(
            @PathVariable("reservationId") int reservationId
    ) throws NotFoundException {
        return reservationService.getOne(reservationId);
    }

    @PostMapping(value = "/delete", consumes = "application/json")
    public String deleteReservations(
            @RequestBody List< Integer > ids){
        String msg = "";

        for(int id : ids){
            if(reservationService.deleteReservation(id)){
                msg = "Usunięto dane";
            }
            else msg = "Wystąpił błąd podczas usuwania danych";
        }

        return msg;
    }

    @PostMapping(value = "/confirm", consumes = "application/json")
    public String confirmReservation(
            @RequestBody int id){
        String msg = "";

        if(reservationService.confirmReservation(id)){
           msg = "Potwierdzono rezerwację o id: " + id;
        }else{
            msg = "Rezerwacja jest już potwierdzona";
        }

        return msg;
    }
}
