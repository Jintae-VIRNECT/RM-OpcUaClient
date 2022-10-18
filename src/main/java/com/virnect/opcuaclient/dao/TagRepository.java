package com.virnect.opcuaclient.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virnect.opcuaclient.domain.Tag;

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
public interface TagRepository extends JpaRepository<Tag, Long>, TagRepositoryCustom {

}
