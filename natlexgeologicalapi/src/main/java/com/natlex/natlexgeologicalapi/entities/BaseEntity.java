package com.natlex.natlexgeologicalapi.entities;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract class BaseEntity {

	@Column(name = "created_date", nullable = false)
	@CreatedDate
	private Instant createdDate;

	@Column(name = "last_modified_date", nullable = false)
	@LastModifiedDate
	private Instant lastModifiedDate;

}
