package com.natlex.natlexgeologicalapi.services.impl;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.natlex.natlexgeologicalapi.dto.ImportFileDto;
import com.natlex.natlexgeologicalapi.entities.Geological;
import com.natlex.natlexgeologicalapi.entities.ImportFile;
import com.natlex.natlexgeologicalapi.enums.FileJobStatus;
import com.natlex.natlexgeologicalapi.exceptions.NotFoundException;
import com.natlex.natlexgeologicalapi.mapper.SelmaMapper;
import com.natlex.natlexgeologicalapi.repositories.ImportFileRepository;
import com.natlex.natlexgeologicalapi.services.ImportFileAsyncService;
import com.natlex.natlexgeologicalapi.services.ImportFileService;
import com.natlex.natlexgeologicalapi.services.StorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class ImportFileServiceImpl implements ImportFileService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImportFileServiceImpl.class);
	
	private final ImportFileRepository importFileRepository;
	
	private final ImportFileAsyncService importFileAsyncService;
	
	private final StorageService storageService;

	
	@Override
	public ImportFileDto startImportFile(MultipartFile file) {
		ImportFile importFile = new ImportFile();
		importFile.setFileJobStatus(FileJobStatus.IN_PROGRESS);
		importFile.setFileName(file.getOriginalFilename());
		importFile = importFileRepository.save(importFile);
		try {
			Path storedImportFile = storageService.store(file);
			importFileAsyncService.importFileAsync(importFile, storedImportFile);
		} catch (Exception e) {
			LOGGER.error("startImportFile error:",e);
			importFile.setFileJobStatus(FileJobStatus.ERROR);
			importFile = importFileRepository.save(importFile);
		}
		return SelmaMapper.INSTANCE.asImportFileDto(importFile);
	}
	
	@Override
	public ImportFileDto getImportFile(long id) throws NotFoundException {
		return SelmaMapper.INSTANCE.asImportFileDto(getImportFileInner(id));
	}
	
	private ImportFile getImportFileInner(long id) throws NotFoundException {
		return importFileRepository.findById(id).orElseThrow(() -> new NotFoundException(Geological.class, id));
	}

}
