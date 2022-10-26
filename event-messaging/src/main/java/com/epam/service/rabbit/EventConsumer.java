package com.epam.service.rabbit;

import com.epam.model.Event;
import com.epam.service.EventService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("rabbit")
public class EventConsumer {

    private final EventService service;

    public EventConsumer(EventService service) {
        this.service = service;
    }

    @RabbitListener(queues = "create-event-request", containerFactory = "listenerContainerFactory")
    public void createEvent(List<Event> events) {
        events.forEach(service::createEvent);
    }

    @RabbitListener(queues = "update-event-request", containerFactory = "listenerContainerFactory")
    public void updateEvent(List<Event> events) {
        events.forEach(event -> service.updateEvent(event.getEventId(), event));
    }

    @RabbitListener(queues = "delete-event-request", containerFactory = "listenerContainerFactory")
    public void deleteEvent(List<Long> eventIds) {
        eventIds.forEach(service::deleteEvent);
    }
}
