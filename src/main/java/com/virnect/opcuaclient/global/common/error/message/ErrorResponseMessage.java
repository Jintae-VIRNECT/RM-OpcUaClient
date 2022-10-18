package com.virnect.opcuaclient.global.common.error.message;

import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@Getter
@NoArgsConstructor
@ApiModel
public class ErrorResponseMessage {
	@ApiModelProperty(value = "에러 응답 코드")
	private int code;
	@ApiModelProperty(value = "에러 응답 서비스명")
	private String service;
	@ApiModelProperty(value = "에러 응답 메시지")
	private String message;
	@ApiModelProperty(value = "에러 응답 데이터")
	private Map<String, Object> data;

	public ErrorResponseMessage(final OpcUaClientErrorCode error) {
		this.code = error.getCode();
		this.service = "opc-uaClient";
		this.message = error.getMessage();
		data = new HashMap<>();
	}
}
