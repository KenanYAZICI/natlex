package com.natlex.natlexgeologicalapi.services;

import org.springframework.web.multipart.MultipartFile;

import com.natlex.natlexgeologicalapi.dto.ImportFileDto;
import com.natlex.natlexgeologicalapi.exceptions.NotFoundException;

public interface ImportFileService {

	public ImportFileDto startImportFile(MultipartFile file);

	public ImportFileDto getImportFile(long id) throws NotFoundException;
}
