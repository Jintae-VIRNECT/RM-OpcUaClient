package com.virnect.opcuaclient.application;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.virnect.opcuaclient.dto.response.PushResponse;

/**
 * Project        : RM-OpcUaClient
 * DATE           : 2022-09-28
 * AUTHOR         : VIRNECT (Jintae Kim)
 * EMAIL          : jtkim@virnect.com
 * DESCRIPTION    :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-09-28      VIRNECT          최초 생성
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMqService {
	private final String ROUTING_KEY = "opcua.remote.enter";
	private final RabbitTemplate rabbitTemplate;

	@RabbitListener(
		bindings = @QueueBinding(
			value = @Queue(
				name = "opcua.queue",
				arguments = {@Argument(name = "x-dead-letter-exchange", value = "dlx"),
					@Argument(name = "x-dead-letter-routing-key", value = "dlx.push")}
			),
			exchange = @Exchange(value = "amq.fanout", type = ExchangeTypes.FANOUT)
		), containerFactory = "rabbitListenerContainerFactory")
	public void getOpcUaData(PushResponse pushResponse) {
		log.debug("getOpcUaData {}", pushResponse);
		System.out.println("pushResponse fanout= " + pushResponse);
	}

	@RabbitListener(
		bindings = @QueueBinding(
			value = @Queue(
				arguments = {@Argument(name = "x-dead-letter-exchange", value = "dlx"),
					@Argument(name = "x-dead-letter-routing-key", value = "dlx.push")}
			),
			exchange = @Exchange(value = "amq.topic", type = ExchangeTypes.TOPIC),
			key = ROUTING_KEY
		), containerFactory = "rabbitListenerContainerFactory")
	public void getOpcUaDataTopic() {
		System.out.println("enter opc ua= ");
	}
	// public void topicSender(PushResponse pushResponse) {
	// 	rabbitTemplate.convertAndSend("amq.topic", ROUTING_KEY, pushResponse);
	// }

	public void fanoutSender(PushResponse pushResponse) {
		rabbitTemplate.convertAndSend("amq.fanout", null, pushResponse);
	}

}
