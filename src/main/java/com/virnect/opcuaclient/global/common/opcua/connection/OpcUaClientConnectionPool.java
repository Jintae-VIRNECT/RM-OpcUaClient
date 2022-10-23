package com.virnect.opcuaclient.global.common.opcua.connection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import javax.annotation.PreDestroy;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.SessionActivityListener;
import org.eclipse.milo.opcua.sdk.client.api.UaClient;
import org.eclipse.milo.opcua.sdk.client.api.UaSession;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.ServiceFault;
import org.eclipse.milo.opcua.stack.core.util.EndpointUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import com.virnect.opcuaclient.error.OpcUaClientErrorCode;
import com.virnect.opcuaclient.global.common.exception.OpcUaClientException;
import com.virnect.opcuaclient.global.config.property.OpcUaProperties;

/**
 * Project        : RM-OpcUaClient
 * DATE           : 2022-09-27
 * AUTHOR         : VIRNECT (Jintae Kim)
 * EMAIL          : jtkim@virnect.com
 * DESCRIPTION    :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-09-27      VIRNECT          최초 생성
 */

@Slf4j
@Component
public class OpcUaClientConnectionPool implements InitializingBean {
	private static OpcUaProperties opcUaProperties;
	private static BlockingQueue<Optional<OpcUaClient>> connectionPool;
	private static BlockingQueue<OpcUaClient> usedConnection;

	@Autowired
	public OpcUaClientConnectionPool(OpcUaProperties opcUaProperties) {
		this.opcUaProperties = opcUaProperties;
	}

	@Override
	public void afterPropertiesSet() {
		create();
	}

	private static void create() {
		log.info("static pool creat Starting...");
		int initialPoolSize = opcUaProperties.getPoolSize();
		connectionPool = new LinkedBlockingDeque<>(initialPoolSize);
		usedConnection = new LinkedBlockingDeque<>(initialPoolSize);

		for (int i = 0; i < initialPoolSize; i++) {
			connectionPool.add(Optional.of(createConnection()));
		}

		log.info("static pool created");

	}

	public OpcUaClient getConnection() {
		Optional<OpcUaClient> connection = connectionPool.poll();

		if (Objects.requireNonNull(connection).isPresent()) {

			OpcUaClient client = connection.get();

			try {
				client.connect().get();
			} catch (Exception e) {
				e.printStackTrace();
			}

			boolean offer = usedConnection.offer(client);

			log.info("getConnection Succeeded ? :::  {}", offer);

			logConnectionStatus("getConnection");

			return client;
		} else {
			if (usedConnection.size() < opcUaProperties.getPoolSize()) {
				log.warn("additional connection created after startup");
				OpcUaClient client = createConnection();
				connectionPool.add(Optional.ofNullable(client));
				return getConnection();
			} else {
				throw new OpcUaClientException(OpcUaClientErrorCode.ERR_UNEXPECTED_SERVER_ERROR);
			}
		}
	}

	public <T extends UaClient> boolean releaseConnection(T connection) {
		connectionPool.offer(Optional.ofNullable((OpcUaClient)connection));
		boolean removed = usedConnection.remove(connection);
		logConnectionStatus("releaseConnection");
		return removed;
	}

	public void shutdown() {
		usedConnection.forEach(this::releaseConnection);
		for (Optional<OpcUaClient> client : connectionPool) {
			client.ifPresent(OpcUaClient::disconnect);
		}

		connectionPool.clear();

		log.info("connection all cleared");
	}

	public void stopSubscription() {
		usedConnection.forEach(this::releaseConnection);
		for (Optional<OpcUaClient> client : connectionPool) {
			client.ifPresent(opcUaClient -> opcUaClient.getSubscriptionManager().clearSubscriptions());
		}

		log.info("stop Subscription");
	}

	public void disconnect() {
		usedConnection.forEach(this::releaseConnection);
		for (Optional<OpcUaClient> client : connectionPool) {
			client.ifPresent(OpcUaClient::disconnect);

		}
		log.info("disconnect client");
	}

	private static OpcUaClient createConnection() {

		final String SERVER_HOSTNAME = opcUaProperties.getHost();
		final int SERVER_PORT = Integer.parseInt(opcUaProperties.getPort());

		try {
			List<EndpointDescription> endpoints
				= DiscoveryClient.getEndpoints("opc.tcp://" + SERVER_HOSTNAME + ":" + SERVER_PORT).get();
			EndpointDescription configPoint = EndpointUtil.updateUrl(
				endpoints.get(0), SERVER_HOSTNAME, SERVER_PORT);

			OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();
			cfg.setEndpoint(configPoint);

			OpcUaClient client = OpcUaClient.create(cfg.build());
			client.addSessionActivityListener(getSession_active());
			client.addFaultListener(OpcUaClientConnectionPool::onServiceFault);

			client.connect().get();

			return client;

		} catch (Throwable t) {
			t.printStackTrace();
			throw new RuntimeException("Connection not available", t);
		}
	}

	private void logConnectionStatus(String methodName) {
		int initialPoolSize = opcUaProperties.getPoolSize();
		log.info(methodName + "called. connectionPool's available capacity : "
			+ (initialPoolSize - connectionPool.remainingCapacity()));
		log.info(methodName + "called. usedPool's current capacity : "
			+ (initialPoolSize - usedConnection.remainingCapacity()));
	}

	private static void onServiceFault(ServiceFault serviceFault) {
		log.info("server disconnected {} ", serviceFault.getResponseHeader().getServiceResult());
		log.info("===> reconnect opc ua server");
		create();
	}

	@NotNull
	private static SessionActivityListener getSession_active() {
		return new SessionActivityListener() {
			@Override
			public void onSessionActive(UaSession session) {
				SessionActivityListener.super.onSessionActive(session);
				log.info("session active");
			}

			@Override
			public void onSessionInactive(UaSession session) {
				SessionActivityListener.super.onSessionInactive(session);
				log.info("session Inactive");
			}
		};
	}

	@PreDestroy
	public void destroy() {
		shutdown();
	}

	public Map<String, Integer> checkPool() {
		int usedPoolSize = usedConnection.size();
		int connectionPoolSize = connectionPool.size();

		Map<String, Integer> pool = new HashMap<>();

		pool.put("usedPool", usedPoolSize);
		pool.put("connectionPool", connectionPoolSize);
		return pool;
	}
}
