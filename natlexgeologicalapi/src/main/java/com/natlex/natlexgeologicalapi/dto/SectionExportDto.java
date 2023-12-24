package com.natlex.natlexgeologicalapi.dto;

import java.util.List;

import lombok.Data;

@Data
public class SectionExportDto {

	private String name;
	
	private List<GeologicalExportDto> geologicals;
}
