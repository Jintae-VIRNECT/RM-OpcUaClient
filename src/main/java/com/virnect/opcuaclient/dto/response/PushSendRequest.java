package com.virnect.opcuaclient.dto.response;

import java.util.Map;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Project        : RM-OpcUaClient
 * DATE           : 2022-10-18
 * AUTHOR         : VIRNECT (Jintae Kim)
 * EMAIL          : jtkim@virnect.com
 * DESCRIPTION    :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-10-18      VIRNECT          최초 생성
 */
@Getter
@NoArgsConstructor
public class PushSendRequest {

	@ApiModelProperty(value = "푸시 발송 서비스 명", example = "pf-workspace", required = true, position = 0)
	@NotBlank
	private String service;

	@ApiModelProperty(value = "워크스페이스 식별자", example = "4ff0606102fbe", required = true, position = 1)
	@NotBlank
	private String workspaceId;
	
	@ApiModelProperty(value = "이벤트 이름", example = "WORKSPACE_EXPELED", required = true, position = 4)
	@NotBlank
	private String event;

	@ApiModelProperty(value = "메세지 내용", example = "{\n" +
		"  \"custom1\": \"string\",\n" +
		"  \"custom2\": \"string\"\n" +
		"}", required = true, position = 5)
	private Map<Object, Object> contents;

}
