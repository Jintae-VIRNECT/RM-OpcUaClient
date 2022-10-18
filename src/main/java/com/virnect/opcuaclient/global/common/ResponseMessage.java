package com.virnect.opcuaclient.global.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
@NoArgsConstructor
public class ResponseMessage {

	Map<String, Object> data = new ConcurrentHashMap<>();
	int code = 200;
	String message = "complete";

	public ResponseMessage(Map<String, Object> data) {
		this.data = data;
	}

	public ResponseMessage addParam(String key, Object object) {
		this.data.put(key, object);
		return this;
	}
}
