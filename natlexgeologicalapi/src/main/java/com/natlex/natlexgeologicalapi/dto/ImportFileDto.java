package com.natlex.natlexgeologicalapi.dto;

import com.natlex.natlexgeologicalapi.enums.FileJobStatus;

import lombok.Data;

@Data
public class ImportFileDto {

	private long id;
	
	private FileJobStatus fileJobStatus;
	
	private String fileName;
}
