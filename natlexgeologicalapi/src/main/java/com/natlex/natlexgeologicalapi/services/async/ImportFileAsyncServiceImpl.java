package com.natlex.natlexgeologicalapi.services.async;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.natlex.natlexgeologicalapi.entities.Geological;
import com.natlex.natlexgeologicalapi.entities.ImportFile;
import com.natlex.natlexgeologicalapi.entities.Section;
import com.natlex.natlexgeologicalapi.enums.FileJobStatus;
import com.natlex.natlexgeologicalapi.repositories.ImportFileRepository;
import com.natlex.natlexgeologicalapi.repositories.SectionRepository;
import com.natlex.natlexgeologicalapi.services.ImportFileAsyncService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class ImportFileAsyncServiceImpl implements ImportFileAsyncService {

	private final SectionRepository sectionRepository;

	private final ImportFileRepository importFileRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImportFileAsyncServiceImpl.class);

	@Async
	@Override
	@Transactional
	public void importFileAsync(ImportFile importFile, Path storedImportFile) {

		try (InputStream inputStream = new FileInputStream(storedImportFile.toFile())) {
			Workbook workbook = new XSSFWorkbook(inputStream);
			// Delay for demonstration purposes
			Thread.sleep(10000L);
			// Assuming excel file contains one sheet, otherwise another for loop can be
			// added here?
			Sheet sheet = workbook.getSheetAt(0);
			// remove the header row
			sheet.removeRow(sheet.getRow(0));

			for (Row row : sheet) {

				Cell sectionCell = row.getCell(0);
				String sectionName = sectionCell.getStringCellValue();
				Section section = null;
				Optional<Section> sectionOptional = sectionRepository.findOneByName(sectionName);

				if (sectionOptional.isEmpty()) {
					section = new Section();
					section.setName(sectionName);

				} else {
					section = sectionOptional.get();
				}

				for (int i = 1; i < row.getLastCellNum(); i = i + 2) {
					Cell geoNameCell = row.getCell(i);
					Cell geoCodeCell = row.getCell(i + 1);
					// Both geo cells must be not null for record to be valid?
					if (geoNameCell != null && geoCodeCell != null) {
						String geologicalName = geoNameCell.getStringCellValue();
						String geologicalCode = row.getCell(i + 1).getStringCellValue();

						Geological geological = new Geological();
						geological.setCode(geologicalCode);
						geological.setName(geologicalName);
						// Optional control, if the record with same geo code exists do not save?
						boolean isGeoRecordExists = false;
						if (CollectionUtils.isNotEmpty(section.getGeologicals())) {
							isGeoRecordExists = section.getGeologicals().stream()
									.anyMatch(geo -> StringUtils.equals(geo.getCode(), geologicalCode));
						} else {
							section.setGeologicals(new ArrayList<>());
						}

						if (!isGeoRecordExists) {
							geological.setSection(section);
							section.getGeologicals().add(geological);
						}
					}
				}
				section = sectionRepository.save(section);
				LOGGER.info(MessageFormat.format("section with name {0} saved", sectionName));
				importFile.setFileJobStatus(FileJobStatus.DONE);
			}
			workbook.close();
		} catch (Exception e) {
			importFile.setFileJobStatus(FileJobStatus.ERROR);
			LOGGER.error("importFileAsync error:", e);
		} finally {
			importFileRepository.save(importFile);

		}
	}

}
