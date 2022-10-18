package com.virnect.opcuaclient.global.config;

import javax.annotation.PostConstruct;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.virnect.opcuaclient.global.config.property.RabbitmqProperty;

/**
 * Project        : RM-OpcUaClient
 * DATE           : 2022-09-27
 * AUTHOR         : VIRNECT (Jintae Kim)
 * EMAIL          : jtkim@virnect.com
 * DESCRIPTION    :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-09-27      VIRNECT          최초 생성
 */
@RequiredArgsConstructor
@Slf4j
@Configuration
public class RabbitmqConfiguration {

	private final RabbitmqProperty rabbitmqProperty;
	private static final String OPC_UA_QUEUE_NAME = "opcua.queue";
	private static final String OPC_UA_EXCHANGE_NAME = "opcua.exchange";
	private static final String ROUTING_KEY = "opcua.*";

	@PostConstruct
	public void init() {
		log.info("[RABBITMQ_CONFIG] Host >> [{}]", rabbitmqProperty.getHost());
		log.info("[RABBITMQ_CONFIG] Port >> [{}]", rabbitmqProperty.getPort());
		log.info("[RABBITMQ_CONFIG] VirtualHost >> [{}]", rabbitmqProperty.getVirtualHost());
		log.info("[RABBITMQ_CONFIG] Username >> [{}], Password >> [{}]",
			rabbitmqProperty.getUsername(), rabbitmqProperty.getPassword()
		);
	}

	@Bean
	public CachingConnectionFactory cachingConnectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setPort(rabbitmqProperty.getPort());
		connectionFactory.setUsername(rabbitmqProperty.getUsername());
		connectionFactory.setPassword(rabbitmqProperty.getPassword());
		connectionFactory.setHost(rabbitmqProperty.getHost());
		connectionFactory.setVirtualHost(rabbitmqProperty.getVirtualHost());
		connectionFactory.setPublisherReturns(true);

		return connectionFactory;
	}

	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter());
		return rabbitTemplate;
	}

}
