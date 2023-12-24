package com.natlex.natlexgeologicalapi.services;

import com.natlex.natlexgeologicalapi.entities.ExportFile;

public interface ExportFileAsyncService {

	public void exportFileAsync(String fileName, ExportFile exportFile );
}
