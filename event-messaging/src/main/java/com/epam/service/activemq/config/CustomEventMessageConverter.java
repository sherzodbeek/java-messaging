package com.epam.service.activemq.config;

import com.epam.model.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.List;


public class CustomEventMessageConverter implements MessageConverter {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Message toMessage(Object o, Session session) throws JMSException, MessageConversionException {
        List<Event> events = (List<Event>) o;
        String payload = null;
        try {
             payload = objectMapper.writeValueAsString(events);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        TextMessage message =  session.createTextMessage();
        message.setText(payload);
        return message;
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        TextMessage textMessage = (TextMessage) message;
        String payload = textMessage.getText();
        List<Event> events = null;
        try {
            events = objectMapper.readValue(payload, new TypeReference<List<Event>>(){});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }
}
