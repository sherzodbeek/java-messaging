package com.epam.service.kafka;

import com.epam.model.Event;
import com.epam.service.EventService;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("kafka")
public class EventConsumer {

    private final EventService service;

    public EventConsumer(EventService service) {
        this.service = service;
    }

    @KafkaListener(topics = "create-event-request", containerFactory = "kafkaListenerContainerFactory")
    public void createEvent(List<Event> events) {
        events.forEach(service::createEvent);
    }

    @KafkaListener(topics = "update-event-request", containerFactory = "kafkaListenerContainerFactory")
    public void updateEvent(List<Event> events) {
        events.forEach(event -> service.updateEvent(event.getEventId(), event));
    }

    @KafkaListener(topics = "delete-event-request", containerFactory = "idsListenerContainerFactory")
    public void deleteEvent(List<Long> eventIds) {
        eventIds.forEach(service::deleteEvent);
    }


}
