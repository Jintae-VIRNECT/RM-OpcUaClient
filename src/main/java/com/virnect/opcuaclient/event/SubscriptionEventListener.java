package com.virnect.opcuaclient.event;

import java.util.stream.Collectors;

import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscriptionManager;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DateTime;
import org.eclipse.milo.opcua.stack.core.types.builtin.StatusCode;

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class SubscriptionEventListener implements UaSubscriptionManager.SubscriptionListener {

	@Override
	public void onKeepAlive(
		UaSubscription subscription,
		DateTime publishTime
	) {
		log.info(
			"onKeepAlive event,{}",
			subscription.getMonitoredItems().stream().map(UaMonitoredItem::getStatusCode).collect(
				Collectors.toList())
		);

	}

	@Override
	public void onStatusChanged(
		UaSubscription subscription,
		StatusCode status
	) {
		log.info("onStatusChanged event for {}, status = {}", subscription.toString(), status.toString());
	}

	@Override
	public void onPublishFailure(UaException exception) {
		log.error("onPublishFailure exception {}", exception.toString());
	}

	@Override
	public void onNotificationDataLost(UaSubscription subscription) {
		log.error("onNotificationDataLost event for {}", subscription.toString());
	}

	@Override
	public void onSubscriptionTransferFailed(
		UaSubscription subscription,
		StatusCode statusCode
	) {
		log.error("onSubscriptionTransferFailed event for {}, status = {}", subscription.toString(),
			statusCode.toString()
		);
	}

}
