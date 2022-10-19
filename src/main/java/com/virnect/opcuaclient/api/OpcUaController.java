package com.virnect.opcuaclient.api;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.virnect.opcuaclient.application.OpcUaService;
import com.virnect.opcuaclient.dto.response.PushSendRequest;
import com.virnect.opcuaclient.global.common.ApiResponse;
import com.virnect.opcuaclient.global.common.opcua.connection.OpcUaClientConnectionPool;

/**
 * Project        : RM-OpcUaClient
 * DATE           : 2022-10-04
 * AUTHOR         : VIRNECT (Jintae Kim)
 * EMAIL          : jtkim@virnect.com
 * DESCRIPTION    :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-10-04      VIRNECT          최초 생성
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class OpcUaController {
	private final OpcUaService opcUaService;
	private final OpcUaClientConnectionPool opcUaClientConnectionPool;

	@CrossOrigin("*")
	@ApiOperation(value = "OPC-UA-Subscription 시작", notes = "opc-ua 서버에서 node Id들로 구독 실행.")
	@GetMapping("/start")
	public ResponseEntity<ApiResponse<String>> start() {

		opcUaService.startSubscription();

		return ResponseEntity.ok(new ApiResponse<>("ok"));

	}

	@CrossOrigin("*")
	@GetMapping("/create")
	public void create() {
		opcUaClientConnectionPool.afterPropertiesSet();
	}

	@CrossOrigin("*")
	@GetMapping("/check")
	public ResponseEntity<Map<String, Integer>> checkPool() {

		Map<String, Integer> pool = opcUaClientConnectionPool.checkPool();

		return ResponseEntity.ok(pool);
	}

	@MessageMapping("opcua.remote.enter")
	public void enter(PushSendRequest pushRequest) {
		log.info("들어왔는가? {}", pushRequest.getEvent());

		if ("enter".equals(pushRequest.getStatus())) {
			opcUaService.startSubscription();
		}
	}
}
