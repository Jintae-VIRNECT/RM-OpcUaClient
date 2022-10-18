package com.virnect.opcuaclient.global.common.exception;

import com.virnect.opcuaclient.error.exception.BusinessException;
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
public class OpcUaClientException extends BusinessException {

	private final OpcUaClientErrorCode error;

	public OpcUaClientException(OpcUaClientErrorCode error) {
		super(error);
		this.error = error;
	}

	public OpcUaClientErrorCode getError() {
		return this.error;
	}
}
