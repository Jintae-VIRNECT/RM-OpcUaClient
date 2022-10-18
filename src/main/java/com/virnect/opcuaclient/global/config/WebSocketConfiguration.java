package com.virnect.opcuaclient.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.RequiredArgsConstructor;

import com.virnect.opcuaclient.global.websocket.HandShakeInterceptor;
import com.virnect.opcuaclient.global.websocket.StompErrorHandler;
import com.virnect.opcuaclient.global.websocket.StompInboundInterceptor;
import com.virnect.opcuaclient.global.websocket.StompOutboundInterceptor;

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

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
	private final StompInboundInterceptor stompInboundInterceptor;
	private final StompErrorHandler stompErrorHandler;
	private final StompOutboundInterceptor stompOutboundInterceptor;

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.setErrorHandler(stompErrorHandler)
			.addEndpoint("/opcua")
			.setAllowedOrigins("*")
			.addInterceptors(new HandShakeInterceptor());

	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app").setPathMatcher(new AntPathMatcher("."));
		registry.enableStompBrokerRelay("/topic", "/exchange")
			.setRelayHost("192.168.0.21")
			.setSystemLogin("guest")
			.setSystemPasscode("guest");
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(stompInboundInterceptor);
	}

	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		registration.interceptors(stompOutboundInterceptor);
	}

}
