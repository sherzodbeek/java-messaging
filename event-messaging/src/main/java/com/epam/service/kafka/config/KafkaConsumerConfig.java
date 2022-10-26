package com.epam.service.kafka.config;

import com.epam.model.Event;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableKafka
@Configuration
@Profile("kafka")
public class KafkaConsumerConfig {

    private static final String BOOTSTRAP_SERVERS = "127.0.0.1:9092";
    private static final String GROUP_ID = "event-group-id";

    @Bean
    public ConsumerFactory<String, List<Event>> eventConsumerFactory() {

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new EventCustomDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, List<Event>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, List<Event>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(eventConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, List<Long>> idsConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new IdsCustomDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, List<Long>> idsListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, List<Long>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(idsConsumerFactory());
        return factory;
    }
}
