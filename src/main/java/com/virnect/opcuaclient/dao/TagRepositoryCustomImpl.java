package com.virnect.opcuaclient.dao;

import static com.virnect.opcuaclient.domain.QTag.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import com.virnect.opcuaclient.dto.response.query.TagResponseDto;

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
@Repository
@RequiredArgsConstructor
public class TagRepositoryCustomImpl implements TagRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<TagResponseDto> findAllTag() {

		List<TagResponseDto> fetch = queryFactory
			.select(Projections.constructor(
				TagResponseDto.class,
				tag.identifier,
				tag.identifierType,
				tag.namespace
			)).from(tag)
			.fetch();

		return fetch;
	}
}
