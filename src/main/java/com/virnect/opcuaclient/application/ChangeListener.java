package com.virnect.opcuaclient.application;

import java.util.List;

import org.eclipse.milo.opcua.sdk.client.subscriptions.ManagedDataItem;
import org.eclipse.milo.opcua.sdk.client.subscriptions.ManagedSubscription;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;

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
public class ChangeListener implements ManagedSubscription.ChangeListener {

	@Override
	public void onDataReceived(List<ManagedDataItem> dataItems, List<DataValue> dataValues) {

		System.out.println(" =============== ");

		for (ManagedDataItem dataItem : dataItems) {
			System.out.println(
				"dataItem.getNodeId().getIdentifier().toString() = " + dataItem.getNodeId().getIdentifier().toString());

		}
		for (DataValue dataValue : dataValues) {
			System.out.println(
				"dataValue.getValue().getValue().toString(); = " + dataValue.getValue().getValue().toString());
		}

	}

}
