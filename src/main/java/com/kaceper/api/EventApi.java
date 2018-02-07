package com.kaceper.api;

import com.kaceper.dto.EventDto;
import com.kaceper.dto.input.ListDto;
import com.kaceper.model.Event;
import com.kaceper.repository.EventRepository;
import com.kaceper.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventApi {

    @Autowired
    EventService eventService;

    @Autowired
    EventRepository eventRepository;

    @GetMapping(value = "/all")
    public ListDto<EventDto> getAllRooms() {
        return ListDto.make( () -> eventService.getAllEvents() );
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public String addRoom(
            @RequestBody EventDto dto){
        String msg = "";

        List<EventDto> list = eventService.getAllEvents();

        for(EventDto e : list){
            if(e.getEventDate().equals(dto.getEventDate())){
                msg = "Podany termin jest zajęty";
                return msg;
            }
        }

        Event r = eventService.addEvent(dto);
        if(r != null){
            msg = "Dodano wydarzenie";
        }else{
            msg = "Wystąpił błąd podczas dodawania wydarzenia";
        }

        return msg;
    }

    @PostMapping(value = "/delete", consumes = "application/json")
    public String deleteRooms(
            @RequestBody List< Integer > ids){
        String msg = "";

        for(int id : ids){
            if(eventService.deleteEvent(id)){
                msg = "Usunięto dane";
            }
            else msg = "Wystąpił błąd podczas usuwania danych";
        }

        return msg;
    }

    @PostMapping(value = "/update", consumes = "application/json")
    public String update(
            @RequestBody EventDto dto
    ){
        String message = "";

        System.out.println(dto.getDetails());
        if(eventService.updateEvent(dto)){
            message = "Uaktualniono wydarzenie";
        }else{
            message = "Nie można było uaktualnić wydarzenia";
        }

        return message;
    }
}
