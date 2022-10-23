package com.virnect.opcuaclient.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.milo.opcua.sdk.client.subscriptions.ManagedDataItem;
import org.eclipse.milo.opcua.sdk.client.subscriptions.ManagedSubscription;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.virnect.opcuaclient.dto.response.PushResponse;

/**
 * Project        : RM-OpcUaClient
 * DATE           : 2022-10-19
 * AUTHOR         : VIRNECT (Jintae Kim)
 * EMAIL          : jtkim@virnect.com
 * DESCRIPTION    :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-10-19      VIRNECT          최초 생성
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class DataChangeListener implements ManagedSubscription.ChangeListener {

	private final RabbitMqService rabbitMqService;

	@Override
	public void onDataReceived(List<ManagedDataItem> dataItems, List<DataValue> dataValues) {
		Map<Object, Object> contents = new HashMap<>();

		for (int i = 0; i < dataItems.size(); i++) {
			contents.put(
				dataItems.get(i).getNodeId().getIdentifier().toString(),
				dataValues.get(i).getValue().getValue().toString()
			);
		}

		PushResponse pushResponse = PushResponse.builder()
			.service("remote")
			.event("opcUa")
			.contents(contents)
			.build();

		System.out.println("pushResponse = " + pushResponse);

		rabbitMqService.fanoutSender(pushResponse);

	}

	@Override
	public void onKeepAliveReceived() {

		log.info("onKeepAlive event,{}");
	}

}
