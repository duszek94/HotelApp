package com.kaceper.service;

import com.kaceper.dto.EventDto;
import com.kaceper.model.Event;

import java.util.List;

public interface EventService {
    Event addEvent(EventDto eventDto);
    List<EventDto> getAllEvents();
    boolean deleteEvent(Integer id);
    boolean updateEvent(EventDto eventDto);
}
