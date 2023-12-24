package com.natlex.natlexgeologicalapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.natlex.natlexgeologicalapi.dto.ImportFileDto;
import com.natlex.natlexgeologicalapi.enums.FileJobStatus;
import com.natlex.natlexgeologicalapi.exceptions.NotFoundException;
import com.natlex.natlexgeologicalapi.services.ImportFileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Import")
@RequestMapping(ImportRestController.ENDPOINT)
@AllArgsConstructor
public class ImportRestController {

	public static final String ENDPOINT = "/api/v1/import";
	public static final String PATH_VARIABLE_ID = "id";
	public static final String ENDPOINT_ID = "/{id}";
	
	@Autowired
	private ImportFileService importFileService;
	
	@ApiOperation("Start import file")
	@PostMapping
	public ResponseEntity<ImportFileDto> start(@RequestBody MultipartFile file) {

		ImportFileDto importFileDto = importFileService.startImportFile(file);
		return ResponseEntity.ok().body(importFileDto);
	
	}
	
	@ApiOperation("Get import job status")
	@GetMapping(ENDPOINT_ID)
	public ResponseEntity<FileJobStatus> getImportJobStatus(@PathVariable(PATH_VARIABLE_ID) final long id) {

		FileJobStatus result = null;
		try {
			result = importFileService.getImportFile(id).getFileJobStatus();
			return ResponseEntity.ok().body(result);
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(result);
		}
	}
}
