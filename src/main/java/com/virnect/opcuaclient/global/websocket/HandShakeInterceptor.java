package com.virnect.opcuaclient.global.websocket;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

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
public class HandShakeInterceptor implements HandshakeInterceptor {

	@Override
	public boolean beforeHandshake(
		ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
		Map<String, Object> attributes
	) throws Exception {
		log.debug("[WEBSOCKET_HANDSHAKE][BEFORE] HEADER : {}", request.getHeaders());
		return true;
	}

	@Override
	public void afterHandshake(
		ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception
	) {
		log.debug("[WEBSOCKET_HANDSHAKE][AFTER] HEADER : {}", request.getHeaders());

	}
}
