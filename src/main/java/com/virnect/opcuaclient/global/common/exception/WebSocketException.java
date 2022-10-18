package com.virnect.opcuaclient.global.common.exception;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

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
public class WebSocketException extends MessagingException {
	public WebSocketException(Message<?> message) {
		super(message);
	}

	public WebSocketException(String description) {
		super(description);
	}

	public WebSocketException(String description, Throwable cause) {
		super(description, cause);
	}

	public WebSocketException(Message<?> message, String description) {
		super(message, description);
	}

	public WebSocketException(Message<?> message, Throwable cause) {
		super(message, cause);
	}

	public WebSocketException(Message<?> message, String description, Throwable cause) {
		super(message, description, cause);
	}
}
