package com.virnect.opcuaclient.dto.response.query;

import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Data;

/**
 * Project        : RM-OpcUaClient
 * DATE           : 2022-10-06
 * AUTHOR         : VIRNECT (Jintae Kim)
 * EMAIL          : jtkim@virnect.com
 * DESCRIPTION    :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-10-06      VIRNECT          최초 생성
 */
@Data
public class TagResponseDto {

	private String identifier;
	private String identifierType;
	private int namespace;

	@Builder
	@QueryProjection
	public TagResponseDto(String identifier, String identifierType, int namespace) {
		this.identifier = identifier;
		this.identifierType = identifierType;
		this.namespace = namespace;
	}

	public static NodeId createNodId(TagResponseDto tag) {

		NodeId nodeId = tag.getIdentifierType().equals("String") ?
			new NodeId(tag.getNamespace(), tag.getIdentifier()) :
			new NodeId(tag.getNamespace(), Integer.parseInt(tag.getIdentifier()));

		return nodeId;
	}
}
