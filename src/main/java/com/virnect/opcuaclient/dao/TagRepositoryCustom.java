package com.virnect.opcuaclient.dao;

import java.util.List;

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
public interface TagRepositoryCustom {

	List<TagResponseDto> findAllTag();
}
