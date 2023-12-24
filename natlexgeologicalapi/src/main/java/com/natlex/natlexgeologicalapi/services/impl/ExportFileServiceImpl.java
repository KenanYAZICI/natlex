package com.natlex.natlexgeologicalapi.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.natlex.natlexgeologicalapi.dto.ExportFileDto;
import com.natlex.natlexgeologicalapi.entities.ExportFile;
import com.natlex.natlexgeologicalapi.entities.Geological;
import com.natlex.natlexgeologicalapi.enums.FileJobStatus;
import com.natlex.natlexgeologicalapi.exceptions.ExportFileHasError;
import com.natlex.natlexgeologicalapi.exceptions.ExportFileInProgress;
import com.natlex.natlexgeologicalapi.exceptions.NotFoundException;
import com.natlex.natlexgeologicalapi.mapper.SelmaMapper;
import com.natlex.natlexgeologicalapi.repositories.ExportFileRepository;
import com.natlex.natlexgeologicalapi.services.ExportFileAsyncService;
import com.natlex.natlexgeologicalapi.services.ExportFileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class ExportFileServiceImpl implements ExportFileService {

	private final ExportFileRepository exportFileRepository;

	private final ExportFileAsyncService exportFileAsyncService;

	@Override
	public ExportFileDto startExportfile() {
		LocalDateTime localDateTimeNow = LocalDateTime.now();
		//A random guid can be added to file name, to prevent same file name generation if another export starts at the same second?
		String fileName = localDateTimeNow.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "_export.xlsx";
		ExportFile exportFile = new ExportFile();
		exportFile.setFileName(fileName);
		exportFile.setFileJobStatus(FileJobStatus.IN_PROGRESS);
		exportFileRepository.save(exportFile);
		exportFileAsyncService.exportFileAsync(fileName, exportFile);

		return SelmaMapper.INSTANCE.asExportFileDto(exportFile);
	}

	@Override
	public ExportFileDto getExportFile(long id) throws NotFoundException {
		return SelmaMapper.INSTANCE.asExportFileDto(getExportFileInner(id));
	}
	
	private ExportFile getExportFileInner(long id) throws NotFoundException {
		return exportFileRepository.findById(id).orElseThrow(() -> new NotFoundException(Geological.class, id));
	}

	@Override
	public byte[] getExportFileExcelFile(long id) throws NotFoundException, ExportFileHasError, ExportFileInProgress {
		
		byte[] result = null;
		ExportFileDto exportFileDto = getExportFile(id);
		if(FileJobStatus.IN_PROGRESS.equals(exportFileDto.getFileJobStatus())) {
			throw new ExportFileInProgress(ExportFile.class, id);
		} else if(FileJobStatus.ERROR.equals(exportFileDto.getFileJobStatus())) {
			throw new ExportFileHasError(ExportFile.class, id);
		}
		
		try ( InputStream ios = new FileInputStream("/natlexexport"+File.separatorChar+exportFileDto.getFileName());ByteArrayOutputStream ous = new ByteArrayOutputStream(); ) {
			byte[] buffer = new byte[4096];	
	        int read = 0;
	        while ((read = ios.read(buffer)) != -1) {
	            ous.write(buffer, 0, read);
	        }
	        result = ous.toByteArray();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
}
