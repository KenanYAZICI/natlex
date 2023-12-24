package com.natlex.natlexgeologicalapi.dto;

import com.natlex.natlexgeologicalapi.enums.FileJobStatus;

import lombok.Data;

@Data
public class ExportFileDto {

	private long id;

	private FileJobStatus fileJobStatus;

	private String fileName;
}
