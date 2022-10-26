package com.epam.service.kafka.config;

import com.epam.model.Event;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EventCustomDeserializer extends JsonDeserializer<List<Event>> {

    @Override
    public List<Event> deserialize(String topic, Headers headers, byte[] data) {
        return deserialize(topic, data);
    }

    @Override
    public List<Event> deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            return objectMapper.readValue(data, new TypeReference<List<Event>>() {
            });
        } catch (IOException e) {
            throw new SerializationException("Can't deserialize data [" + Arrays.toString(data) +
                    "] from topic [" + topic + "]", e);
        }
    }
}
