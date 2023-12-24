package com.natlex.natlexgeologicalapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.natlex.natlexgeologicalapi.enums.FileJobStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "import_file")
@Data
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class ImportFile extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "file_job_status")
	private FileJobStatus fileJobStatus;
	
	@Column(name = "file_name")
	private String fileName;
}
