package com.virnect.opcuaclient.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Project        : RM-OpcUaClient
 * DATE           : 2022-09-28
 * AUTHOR         : VIRNECT (Jintae Kim)
 * EMAIL          : jtkim@virnect.com
 * DESCRIPTION    :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-09-28      VIRNECT          최초 생성
 */

@ApiModel
@Getter
@ToString
@RequiredArgsConstructor
public class PushResponse {
	@ApiModelProperty(value = "메시지 수신 서비스 명", example = "workspace")
	private String service;
	@ApiModelProperty(value = "메시지 이벤트", position = 1, example = "something event")
	private String event;
	@ApiModelProperty(value = "메시지 수신 워크스페이스 식별자", position = 2, example = "4d6eab0860969a50acbfa4599fbb5ae8")
	private String workspaceId;
	@ApiModelProperty(value = "메시지 발신 완료 여부", position = 3, example = "true")
	private boolean isPublished;
	@ApiModelProperty(value = "메시지 발신 일자", position = 4, example = "2020-07-17T15:44:20")
	private LocalDateTime publishDate;
	@ApiModelProperty(value = "메세지 내용", example = "{\n" +
		"  \"custom1\": \"string\",\n" +
		"  \"custom2\": \"string\"\n" +
		"}", required = true, position = 5)
	private Map<Object, Object> contents;

	@Builder
	public PushResponse(
		String service, String event, String workspaceId, boolean isPublished, LocalDateTime publishDate,
		Map<Object, Object> contents
	) {
		this.service = service;
		this.event = event;
		this.workspaceId = workspaceId;
		this.isPublished = isPublished;
		this.publishDate = publishDate;
		this.contents = contents;
	}
}