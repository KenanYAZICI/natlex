package com.natlex.natlexgeologicalapi.services;

import java.util.List;

import com.natlex.natlexgeologicalapi.dto.SectionDto;
import com.natlex.natlexgeologicalapi.dto.SectionExportDto;
import com.natlex.natlexgeologicalapi.dto.SectionSimpleDto;
import com.natlex.natlexgeologicalapi.exceptions.NotFoundException;
import com.natlex.natlexgeologicalapi.exceptions.RecordAlreadyExistsException;

public interface SectionService {

	public SectionDto getSectionByName(String name) throws NotFoundException;
	
	public List<SectionDto> getSectionByGeologicalCode(String geologicalCode) throws NotFoundException;
	
	public SectionDto getSection(long id) throws NotFoundException;
	
	public SectionDto createSection(SectionSimpleDto sectionSimpleDto) throws NotFoundException, RecordAlreadyExistsException;
	
	public SectionDto updateSection(long id, SectionSimpleDto sectionSimpleDto) throws NotFoundException;

	public void deleteSection(long id);

	public List<SectionExportDto> getSectionForExport() throws NotFoundException;
}
