package com.kaceper.service;

import com.kaceper.dto.EventDto;
import com.kaceper.model.Event;
import com.kaceper.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service("eventService")
@Transactional
public class EventServiceImpl implements EventService{

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EntityManager entityManager;

    public List<EventDto> getAllEvents(){
        List<EventDto> list = new ArrayList<>();
        for(Event r : eventRepository.findAll()){
            list.add(EventDto.make(r));
        }

        return list;
    }

    public boolean updateEvent(EventDto eventDto){
        Event e = eventRepository.findOne(eventDto.getEventId());

        e.setDetails(eventDto.getDetails());
        e.setPrice(eventDto.getPrice());
        e.setCountOfGuests(eventDto.getCountOfGuests());
        e.setEventDate(eventDto.getEventDate());
        e.setTittle(eventDto.getTittle());
        e.setFirstname(eventDto.getFirstname());
        e.setLastname(eventDto.getLastname());
        e.setPhone(eventDto.getPhone());
        e.setPaymentMethod(eventDto.getPaymentMethod());

        eventRepository.save(e);

        return true;
    }


    public Event addEvent(EventDto eventDto){
        Event e = new Event();

        e.setDetails(eventDto.getDetails());
        e.setPrice(eventDto.getPrice());
        e.setCountOfGuests(eventDto.getCountOfGuests());
        e.setEventDate(eventDto.getEventDate());
        e.setTittle(eventDto.getTittle());
        e.setFirstname(eventDto.getFirstname());
        e.setLastname(eventDto.getLastname());
        e.setPhone(eventDto.getPhone());
        e.setPaymentMethod(eventDto.getPaymentMethod());

        eventRepository.save(e);

        return e;
    }

    public boolean deleteEvent(Integer id){
        Event e = entityManager.createQuery("select e from Event e " +
                "where e.eventId = :id", Event.class)
                .setParameter("id", id)
                .getSingleResult();

        entityManager.remove(e);

        return true;
    }
}
