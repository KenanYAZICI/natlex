package com.natlex.natlexgeologicalapi.services;

import com.natlex.natlexgeologicalapi.dto.ExportFileDto;
import com.natlex.natlexgeologicalapi.exceptions.ExportFileHasError;
import com.natlex.natlexgeologicalapi.exceptions.ExportFileInProgress;
import com.natlex.natlexgeologicalapi.exceptions.NotFoundException;

public interface ExportFileService {

	
	public ExportFileDto startExportfile();
	
	public ExportFileDto getExportFile(long id) throws NotFoundException;
	
	public byte[] getExportFileExcelFile(long id) throws NotFoundException, ExportFileHasError, ExportFileInProgress;
}
