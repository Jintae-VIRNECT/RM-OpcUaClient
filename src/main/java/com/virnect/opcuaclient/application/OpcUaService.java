package com.virnect.opcuaclient.application;

import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.virnect.opcuaclient.dao.TagRepository;
import com.virnect.opcuaclient.dto.response.PushResponse;
import com.virnect.opcuaclient.dto.response.query.TagResponseDto;
import com.virnect.opcuaclient.event.SubscriptionEventListener;
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

	private final AtomicInteger clientHandles = new AtomicInteger();
	private final OpcUaClientConnectionPool opcUaClientConnectionPool;
	private final TagRepository tagRepository;
	private final RabbitMqService rabbitMqService;

	public void startSubscription() {

		OpcUaClient client = opcUaClientConnectionPool.getConnection();
		SubscriptionEventListener subscriptionListener = new SubscriptionEventListener();
		client.getSubscriptionManager().addSubscriptionListener(subscriptionListener);

		client.getSubscriptionManager()
			.createSubscription(1000)
			.thenApply(this::subscribeTo);

	}

	private CompletableFuture<UaMonitoredItem> subscribeTo(
		UaSubscription subscription
	) {
		// 태그 정보
		List<NodeId> nodeIdList = tagRepository.findAllTag()
			.stream()
			.map(TagResponseDto::createNodId)
			.collect(Collectors.toList());

		// node Id List로 모니터링 아이템 생성
		List<MonitoredItemCreateRequest> requests = nodeIdList.stream()
			.map(this::getMonitoredItemCreateRequest)
			.collect(Collectors.toList());

		UaMonitoredItem.ValueConsumer consumer = (item, value) -> {
			item.setValueConsumer(this::onSubscriptionValue);
		};

		UaSubscription.ItemCreationCallback onItemCreated = //
			(monitoredItem, id) -> monitoredItem.setValueConsumer(consumer);

		return subscription
			.createMonitoredItems(
				TimestampsToReturn.Both,
				requests,
				onItemCreated
			)
			.thenApply(result -> result.get(0));
	}

	private MonitoredItemCreateRequest getMonitoredItemCreateRequest(NodeId nodeId) {
		ReadValueId readValueId = new ReadValueId(nodeId, AttributeId.Value.uid(), null, null);
		MonitoringParameters monitoringParameters = new MonitoringParameters(
			uint(clientHandles.getAndIncrement()), 1_000.0, null, uint(10), true);

		return MonitoredItemCreateRequest.builder()
			.itemToMonitor(readValueId)
			.requestedParameters(monitoringParameters)
			.monitoringMode(MonitoringMode.Reporting)
			.build();
	}

	private void onSubscriptionValue(UaMonitoredItem uaMonitoredItem, DataValue dataValue) {
		log.info("subscription value received: item={}, value={}",
			uaMonitoredItem.getReadValueId().getNodeId(), dataValue.getValue()
		);

		Map<Object, Object> contents = new HashMap<>();

		contents.put(
			uaMonitoredItem.getReadValueId().getNodeId().getIdentifier().toString(),
			dataValue.getValue().getValue().toString()
		);

		PushResponse pushResponse = PushResponse.builder()
			.service("remote")
			.event("opcUa")
			.isPublished(true)
			.workspaceId("aNgtFNEVMCOwG")
			.contents(contents)
			.build();

		rabbitMqService.fanoutSender(pushResponse);
	}
}
