package com.virnect.opcuaclient.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

import com.virnect.opcuaclient.global.common.error.message.ErrorResponseMessage;
import com.virnect.opcuaclient.global.common.exception.OpcUaClientException;

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
@ControllerAdvice()
public class OcpUaClientGlobalExceptionHandler {
	@ExceptionHandler(OpcUaClientException.class)
	public ResponseEntity<ErrorResponseMessage> userServiceException(OpcUaClientException e) {
		log.error("[OPC-UA_Client - EXCEPTION] - MESSAGE: [{}] , DATA: [{}]", e.getMessage(), e.getError());
		return ResponseEntity.ok(new ErrorResponseMessage(OpcUaClientErrorCode.ERR_UNEXPECTED_SERVER_ERROR));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseMessage> generalExceptionHandler(Exception e) {
		log.error("GLOBAL EXCEPTION: ", e);
		return ResponseEntity.ok(new ErrorResponseMessage(OpcUaClientErrorCode.ERR_UNEXPECTED_SERVER_ERROR));
	}
}