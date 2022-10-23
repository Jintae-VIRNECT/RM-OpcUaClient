package com.virnect.opcuaclient.application;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.builtin.ExpandedNodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

/**
 * Project        : RM-OpcUaClient
 * DATE           : 2022-10-24
 * AUTHOR         : VIRNECT (Jintae Kim)
 * EMAIL          : jtkim@virnect.com
 * DESCRIPTION    :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-10-24      VIRNECT          최초 생성
 */
public class NodeIds {
	private NodeIds() {
	}

	private static final Map<NodeId, String> IDS;

	static {
		final LinkedHashMap<NodeId, String> ids = new LinkedHashMap<>();

		for (final Field field : Identifiers.class.getDeclaredFields()) {

			try {
				final Object value = field.get(null);
				if (value instanceof NodeId) {
					ids.put((NodeId)value, field.getName());
				}
			} catch (final Exception e) {
				continue;
			}
		}

		IDS = Collections.unmodifiableMap(ids);
	}

	public static Optional<String> lookup(final ExpandedNodeId id) {
		
		return Optional.ofNullable(IDS.get(id));
	}

}
