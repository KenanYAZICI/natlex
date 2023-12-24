package com.natlex.natlexgeologicalapi.mapper;

import com.natlex.natlexgeologicalapi.dto.ExportFileDto;
import com.natlex.natlexgeologicalapi.dto.GeologicalDto;
import com.natlex.natlexgeologicalapi.dto.GeologicalExportDto;
import com.natlex.natlexgeologicalapi.dto.GeologicalSimpleDto;
import com.natlex.natlexgeologicalapi.dto.ImportFileDto;
import com.natlex.natlexgeologicalapi.dto.SectionDto;
import com.natlex.natlexgeologicalapi.dto.SectionExportDto;
import com.natlex.natlexgeologicalapi.dto.SectionSimpleDto;
import com.natlex.natlexgeologicalapi.entities.ExportFile;
import com.natlex.natlexgeologicalapi.entities.Geological;
import com.natlex.natlexgeologicalapi.entities.ImportFile;
import com.natlex.natlexgeologicalapi.entities.Section;

import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;
import fr.xebia.extras.selma.Selma;

@Mapper(withIgnoreMissing = IgnoreMissing.ALL)
public interface SelmaMapper {

	SelmaMapper INSTANCE = Selma.builder(SelmaMapper.class).build();

	SectionDto asSectionDto(Section section);
	
	SectionExportDto asSectionExportDto(Section section);


	Section asSection(SectionDto sectionDto);

	SectionSimpleDto asSectionSimpleDto(Section section);

	Section asSection(SectionSimpleDto sectionSimpleDto);

	GeologicalDto asGeologicalDto(Geological geological);

	Geological asGeological(GeologicalDto geologicalDto);

	GeologicalSimpleDto asGeologicalSimpleDto(Geological geological);
	
	GeologicalExportDto asGeologicalExportDto(Geological geological);

	Geological asGeological(GeologicalSimpleDto geologicalSimpleDto);
	
	ExportFileDto asExportFileDto(ExportFile exportFile);
	
	ImportFileDto asImportFileDto(ImportFile importFile);

}
