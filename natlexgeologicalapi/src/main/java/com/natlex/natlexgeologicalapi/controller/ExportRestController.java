package com.natlex.natlexgeologicalapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.natlex.natlexgeologicalapi.dto.ExportFileDto;
import com.natlex.natlexgeologicalapi.enums.FileJobStatus;
import com.natlex.natlexgeologicalapi.exceptions.NotFoundException;
import com.natlex.natlexgeologicalapi.services.ExportFileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Export")
@RequestMapping(ExportRestController.ENDPOINT)
@AllArgsConstructor
public class ExportRestController {

	public static final String ENDPOINT = "/api/v1/export";
	public static final String PATH_VARIABLE_ID = "id";
	public static final String ENDPOINT_ID = "/{id}";
	public static final String ENDPOINT_ID_FILE = "/{id}/file";

	@Autowired
	private ExportFileService exportFileService;

	//TODO: THis api might take some filtering parameters to which sections or geologicals to include in export
	@ApiOperation("Start export to excel")
	@GetMapping
	public ResponseEntity<ExportFileDto> exportToExcel() throws IOException, NotFoundException {

		ExportFileDto result = exportFileService.startExportfile();

		return ResponseEntity.ok().body(result);
	}

	@ApiOperation("Get export job status")
	@GetMapping(ENDPOINT_ID)
	public ResponseEntity<FileJobStatus> getExportJobStatus(@PathVariable(PATH_VARIABLE_ID) final long id) {

		FileJobStatus result = null;
		try {
			result = exportFileService.getExportFile(id).getFileJobStatus();
			return ResponseEntity.ok().body(result);
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(result);
		}
	}
	
	@ApiOperation("Get export job file")
	@GetMapping(ENDPOINT_ID_FILE)
	public ResponseEntity<byte[]> getExportExcelFileById(@PathVariable(PATH_VARIABLE_ID) final long id) {

		byte[] result = null;
		try {
			result = exportFileService.getExportFileExcelFile(id);
			return ResponseEntity.ok().body(result);
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(result);
		}
	}
}
