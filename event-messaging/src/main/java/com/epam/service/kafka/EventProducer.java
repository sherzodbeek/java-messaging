package com.epam.service.kafka;

import com.epam.model.Event;
import com.epam.service.EventMessaging;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Profile("kafka")
public class EventProducer implements EventMessaging {

    private final KafkaTemplate<String, Event> kafkaTemplate;

    public EventProducer(KafkaTemplate<String, Event> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void createEvent(Event event) {
        kafkaTemplate.send("create-event-notification", event);
    }

    @Override
    public void updateEvent(Event event) {
        kafkaTemplate.send("update-event-notification", event);
    }

    @Override
    public void deleteEvent(Event event) {
        kafkaTemplate.send("delete-event-notification", event);
    }
}
