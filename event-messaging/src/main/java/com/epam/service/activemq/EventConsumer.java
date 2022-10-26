package com.epam.service.activemq;

import com.epam.model.Event;
import com.epam.service.EventService;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("activemq")
public class EventConsumer {

    private final EventService service;

    public EventConsumer(EventService service) {
        this.service = service;
    }

    @JmsListener(destination = "create-event-request", containerFactory = "jmsListenerContainerFactory")
    public void createEvent(List<Event> events) {
        events.forEach(service::createEvent);
    }

    @JmsListener(destination = "update-event-request", containerFactory = "jmsListenerContainerFactory")
    public void updateEvent(List<Event> events) {
        events.forEach(event -> service.updateEvent(event.getEventId(), event));
    }

    @JmsListener(destination = "delete-event-request", containerFactory = "idsListenerContainerFactory")
    public void deleteEvent(List<Long> eventIds) {
        eventIds.forEach(service::deleteEvent);
    }
}
