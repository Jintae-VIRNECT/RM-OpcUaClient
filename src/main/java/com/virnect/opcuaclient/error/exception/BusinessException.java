package com.virnect.opcuaclient.error.exception;

import com.virnect.opcuaclient.error.OpcUaClientErrorCode;

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
public class BusinessException extends RuntimeException{

	private final OpcUaClientErrorCode error;

	public BusinessException(String message, OpcUaClientErrorCode error) {
		super(message);
		this.error = error;
	}

	public BusinessException(OpcUaClientErrorCode error) {
		super(error.getMessage());
		this.error = error;
	}

	public OpcUaClientErrorCode getError() {
		return error;
	}
}
