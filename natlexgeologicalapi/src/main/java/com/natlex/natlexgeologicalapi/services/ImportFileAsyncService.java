package com.natlex.natlexgeologicalapi.services;

import java.nio.file.Path;

import com.natlex.natlexgeologicalapi.entities.ImportFile;

public interface ImportFileAsyncService {

	public void importFileAsync(ImportFile importFile, Path storedImportFile);
	
}
