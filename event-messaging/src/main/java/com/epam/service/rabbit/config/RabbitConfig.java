package com.epam.service.rabbit.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("rabbit")
public class RabbitConfig {

    private static final String CREATE_EVENT_NOTIFICATION = "create-event-notification";
    private static final String UPDATE_EVENT_NOTIFICATION = "update-event-notification";
    private static final String DELETE_EVENT_NOTIFICATION = "delete-event-notification";

    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";

    private static final String HOST = "localhost";

    @Bean
    Queue creteEventQueue() {
        return new Queue(CREATE_EVENT_NOTIFICATION, true);
    }

    @Bean
    Queue updateEventQueue() {
        return new Queue(UPDATE_EVENT_NOTIFICATION, true);
    }

    @Bean
    Queue deleteEventQueue() {
        return new Queue(DELETE_EVENT_NOTIFICATION, true);
    }

    @Bean
    public MessageConverter jsonMessageConvertor() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(HOST);
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConvertor());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory listenerContainerFactory() {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(new CustomMapper());
        return factory;
    }

}
