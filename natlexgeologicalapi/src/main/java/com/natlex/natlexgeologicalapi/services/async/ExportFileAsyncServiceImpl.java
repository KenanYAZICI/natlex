package com.natlex.natlexgeologicalapi.services.async;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.natlex.natlexgeologicalapi.config.StorageProperties;
import com.natlex.natlexgeologicalapi.dto.GeologicalExportDto;
import com.natlex.natlexgeologicalapi.dto.SectionExportDto;
import com.natlex.natlexgeologicalapi.entities.ExportFile;
import com.natlex.natlexgeologicalapi.enums.FileJobStatus;
import com.natlex.natlexgeologicalapi.exceptions.NotFoundException;
import com.natlex.natlexgeologicalapi.repositories.ExportFileRepository;
import com.natlex.natlexgeologicalapi.services.ExportFileAsyncService;
import com.natlex.natlexgeologicalapi.services.SectionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class ExportFileAsyncServiceImpl implements ExportFileAsyncService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExportFileAsyncServiceImpl.class);

	private final ExportFileRepository exportFileRepository;
	
	private final StorageProperties storageProperties;

	@Autowired
	private SectionService sectionService;

	@Async
	@Override
	public void exportFileAsync(String fileName, ExportFile exportFile) {

			
		Path destinationFile = Paths.get(storageProperties.getExportLocation()).resolve(
				Paths.get(fileName))
				.normalize().toAbsolutePath();
		File currDir = new File(storageProperties.getExportLocation());
		if (!currDir.exists()) {
			currDir.mkdir();
		}		
		/**
		 * TODO: Instead of writing this locally it can be uploaded to a file server, NoSQl,
		 * MongoDb etc.
		 */
		try (FileOutputStream outputStream = new FileOutputStream(destinationFile.toFile());
				Workbook workbook = new XSSFWorkbook();) {
			// Delay for demonstration purposes
			Thread.sleep(10000L);

			List<SectionExportDto> sectionExportDtos = sectionService.getSectionForExport();

			int geologicalListDepth = 0;

			Sheet sheet = workbook.createSheet("Sections");

			sheet.setColumnWidth(0, 4000);

			Row header = sheet.createRow(0);
			Cell headerCell = header.createCell(0);
			headerCell.setCellValue("Section Name");

			for (int i = 0; i < sectionExportDtos.size(); i++) {
				int rowIndex = i + 1;
				SectionExportDto sectionExportDto = sectionExportDtos.get(i);
				Row sectionRow = sheet.createRow(rowIndex);
				Cell sectionCell = sectionRow.createCell(0);
				sectionCell.setCellValue(sectionExportDto.getName());
				if (CollectionUtils.isNotEmpty(sectionExportDto.getGeologicals())) {
					if (geologicalListDepth < sectionExportDto.getGeologicals().size()) {
						geologicalListDepth = sectionExportDto.getGeologicals().size();
					}
					int geoCellIndex = 1;
					for (int j = 0; j < sectionExportDto.getGeologicals().size(); j++) {
						GeologicalExportDto geologicalExportDto = sectionExportDto.getGeologicals().get(j);
						Cell geoNameCell = sectionRow.createCell(geoCellIndex);
						geoNameCell.setCellValue(geologicalExportDto.getName());
						Cell geoCodeCell = sectionRow.createCell(geoCellIndex + 1);
						geoCodeCell.setCellValue(geologicalExportDto.getCode());
						geoCellIndex = geoCellIndex + 2;
					}
				}
			}

			for (int i = 0; i < geologicalListDepth; i++) {
				Cell headerGeoNameCell = header.createCell((i * 2) + 1);
				headerGeoNameCell.setCellValue(MessageFormat.format("Class {0} name", i));
				sheet.setColumnWidth((i * 2) + 1, 4000);
				Cell headerGeoCodeCell = header.createCell((i * 2) + 2);
				headerGeoCodeCell.setCellValue(MessageFormat.format("Class {0} code", i));
				sheet.setColumnWidth((i * 2) + 2, 3000);
			}

			workbook.write(outputStream);
			exportFile.setFileJobStatus(FileJobStatus.DONE);
		} catch (NotFoundException | IOException | InterruptedException e) {
			exportFile.setFileJobStatus(FileJobStatus.ERROR);
			LOGGER.error("exportFileAsync error:", e);
		} finally {
			exportFileRepository.save(exportFile);
		}
	}
}
