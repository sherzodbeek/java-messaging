package com.epam.service;

import com.epam.model.Event;

public interface EventMessaging {

    void createEvent(Event event);

    void updateEvent(Event event);

    void deleteEvent(Event event);
}
