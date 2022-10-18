package com.virnect.opcuaclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.virnect.opcuaclient.global.config.property.OpcUaProperties;
import com.virnect.opcuaclient.global.config.property.RabbitmqProperty;

@SpringBootApplication
@EnableConfigurationProperties({RabbitmqProperty.class, OpcUaProperties.class})
public class OpcUaClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpcUaClientApplication.class, args);
	}

}
