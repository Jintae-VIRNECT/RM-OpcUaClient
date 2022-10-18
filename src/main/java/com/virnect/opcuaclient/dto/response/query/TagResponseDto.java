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

	private String NodeId;
	private int namespace;

	@Builder
	@QueryProjection
	public TagResponseDto(String nodeId, int namespace) {
		NodeId = nodeId;
		this.namespace = namespace;
	}

	public static NodeId createNodId(TagResponseDto tag) {
		// NodeId nodeId = new NodeId(tag.getNamespace(), tag.getNodeId());
		NodeId nodeId = new NodeId(2, 2133458483);

		return nodeId;
	}
}
