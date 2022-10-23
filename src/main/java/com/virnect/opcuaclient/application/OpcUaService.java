package com.virnect.opcuaclient.application;

import java.util.Collections;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.subscriptions.ManagedSubscription;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.virnect.opcuaclient.dao.TagRepository;
import com.virnect.opcuaclient.global.common.opcua.connection.OpcUaClientConnectionPool;

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

@Service
@RequiredArgsConstructor
@Slf4j
public class OpcUaService {

	private final OpcUaClientConnectionPool opcUaClientConnectionPool;
	private final TagRepository tagRepository;
	private final DataChangeListener changeListener;

	public void startManagementSubscription() {
		OpcUaClient client = opcUaClientConnectionPool.getConnection();

		try {
			ManagedSubscription subscription = ManagedSubscription.create(client);
			subscription.addChangeListener(changeListener);

			// List<NodeId> nodeIdList = tagRepository.findAllTag()
			// 	.stream()
			// 	.map(TagResponseDto::createNodId)
			// 	.collect(Collectors.toList());

			NodeId nodeIdList = new NodeId(2, 327392349);

			// subscription.createDataItem(nodeId);

			subscription.createDataItems(Collections.singletonList(nodeIdList));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
