package com.epam.service;

import com.epam.model.Event;
import com.epam.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository repository;
    private final EventMessaging messaging;

    public EventServiceImpl(EventRepository repository, EventMessaging messaging) {
        this.repository = repository;
        this.messaging = messaging;
    }

    @Override
    public Event createEvent(Event event) {
        Event savedEvent = repository.save(event);
        messaging.createEvent(event);
        return savedEvent;
    }

    @Override
    public Event updateEvent(Long id, Event event) {
        Optional<Event> optionalEvent = repository.findById(id);
        if (optionalEvent.isPresent()) {
            event.setEventId(id);
            Event updatedEvent = repository.save(event);
            messaging.updateEvent(updatedEvent);
            return updatedEvent;
        }
        return null;
    }

    @Override
    public Event getEvent(Long id) {
        Optional<Event> optionalEvent = repository.findById(id);
        return optionalEvent.orElse(null);
    }

    @Override
    public Event deleteEvent(Long id) {
        Optional<Event> optionalEvent = repository.findById(id);
        if (optionalEvent.isPresent()) {
            repository.deleteById(id);
            Event deletedEvent = optionalEvent.get();
            messaging.deleteEvent(deletedEvent);
            return deletedEvent;
        }
        return null;
    }

    @Override
    public List<Event> getAllEvents() {
        return repository.findAll();
    }
}
