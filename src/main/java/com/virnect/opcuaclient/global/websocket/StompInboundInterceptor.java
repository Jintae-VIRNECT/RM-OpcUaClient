package com.virnect.opcuaclient.global.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
@RequiredArgsConstructor
@Component
public class StompInboundInterceptor implements ChannelInterceptor {

	@Override
	public Message<?> preSend(
		Message<?> message, MessageChannel channel
	) {
		SimpMessageHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(
			message, SimpMessageHeaderAccessor.class);

		MessageHeaders messageHeaders = accessor.getMessageHeaders();
		Object nativeHeaders = messageHeaders.get(NativeMessageHeaderAccessor.NATIVE_HEADERS);
		log.debug(
			"[INBOUND][STOMP_MESSAGE] COMMAND : {}, SESSION_ID : {}, NATIVE_HEADERS : {}", accessor.getMessageType(),
			accessor.getSessionId(), nativeHeaders
		);

		return message;
	}
}