package com.virnect.opcuaclient.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.ColumnDefault;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tag_id")
	private Long id;

	private String name;

	@ColumnDefault("2")
	private int namespace;

	@NotBlank
	@Column(unique = true)
	private String nodeId;

	@Builder
	public Tag(Long id, String name, int namespace, String nodeId) {
		this.id = id;
		this.name = name;
		this.namespace = namespace;
		this.nodeId = nodeId;
	}
}
