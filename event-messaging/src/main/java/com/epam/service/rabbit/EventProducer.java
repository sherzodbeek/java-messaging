package com.epam.service.rabbit;

import com.epam.model.Event;
import com.epam.service.EventMessaging;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("rabbit")
public class EventProducer implements EventMessaging {

    private final RabbitTemplate rabbitTemplate;

    public EventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void createEvent(Event event) {
        rabbitTemplate.convertAndSend("create-event-notification", event);
    }

    @Override
    public void updateEvent(Event event) {
        rabbitTemplate.convertAndSend("update-event-notification", event);
    }

    @Override
    public void deleteEvent(Event event) {
        rabbitTemplate.convertAndSend("delete-event-notification", event);
    }
}
