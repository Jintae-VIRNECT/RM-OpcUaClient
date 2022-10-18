package com.virnect.opcuaclient.global.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.virnect.opcuaclient.error.OpcUaClientErrorCode;
import com.virnect.opcuaclient.global.common.error.message.ErrorResponseMessage;

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
@Profile({"!staging", "!production"})
@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfiguration {
	private final ObjectMapper objectMapper;
	@Bean
	public Docket docket() throws JsonProcessingException {
		List<ResponseMessage> responseMessages = new ArrayList<>();
		for (OpcUaClientErrorCode errorCode : OpcUaClientErrorCode.values()) {
			responseMessages.add(new ResponseMessageBuilder().code(errorCode.getCode())
				.message(objectMapper.writeValueAsString(new ErrorResponseMessage(errorCode)))
				.build());
		}

		// API 문서 관련 정보 입력
		ApiInfo apiInfo = new ApiInfoBuilder()
			.contact(new Contact("김진태", "https://virnect.com", "jtkim@vinrect.com"))
			.description("Opc-Ua Client 서버 API 정보 입니다.")
			.version("v0.0.1")
			.title("VIRNECT OPC-UA Client API Document.")
			.license("VIRNECT INC All rights reserved.")
			.build();


		// API 문서 생성 시 필요한 설정 정보
		return new Docket(DocumentationType.SWAGGER_2)
			.useDefaultResponseMessages(false)
			.globalResponseMessage(RequestMethod.GET, responseMessages)
			.globalResponseMessage(RequestMethod.POST, responseMessages)
			.globalResponseMessage(RequestMethod.PUT, responseMessages)
			.globalResponseMessage(RequestMethod.DELETE, responseMessages)
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.virnect.opcuaclient.api"))
			.paths(PathSelectors.any())
			.build()
			.apiInfo(apiInfo);
	}
}

