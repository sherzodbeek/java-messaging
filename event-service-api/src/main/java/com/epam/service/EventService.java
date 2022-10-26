package com.epam.service;

import com.epam.model.Event;

import java.util.List;

public interface EventService {

    Event createEvent(Event event);

    Event updateEvent(Long id, Event event);

    Event getEvent(Long id);

    Event deleteEvent(Long id);

    List<Event> getAllEvents();
}
