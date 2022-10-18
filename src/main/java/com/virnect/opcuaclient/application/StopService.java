package com.virnect.opcuaclient.application;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.virnect.opcuaclient.global.common.opcua.connection.OpcUaClientConnectionPool;

/**
 * Project        : RM-OpcUaClient
 * DATE           : 2022-10-05
 * AUTHOR         : VIRNECT (Jintae Kim)
 * EMAIL          : jtkim@virnect.com
 * DESCRIPTION    :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-10-05      VIRNECT          최초 생성
 */

@RequiredArgsConstructor
@Service
@Slf4j
public class StopService {
	private final OpcUaClientConnectionPool opcUaClientConnectionPool;

	public void stopTask() {

		opcUaClientConnectionPool.stopSubscription();

	}

	public void disconnect() {

		opcUaClientConnectionPool.disconnect();
	}

	public void poolClear() {

		opcUaClientConnectionPool.shutdown();

	}
}
