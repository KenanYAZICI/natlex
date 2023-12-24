package com.natlex.natlexgeologicalapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.natlex.natlexgeologicalapi.dto.SectionDto;
import com.natlex.natlexgeologicalapi.dto.SectionSimpleDto;
import com.natlex.natlexgeologicalapi.exceptions.NotFoundException;
import com.natlex.natlexgeologicalapi.exceptions.RecordAlreadyExistsException;
import com.natlex.natlexgeologicalapi.services.SectionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Sections")
@RequestMapping(SectionRestController.ENDPOINT)
@AllArgsConstructor
public class SectionRestController {

	public static final String ENDPOINT = "/api/v1/sections/";
	public static final String PATH_VARIABLE_ID = "id";
	public static final String ENDPOINT_ID = "/{id}";

	@Autowired
	private SectionService sectionService;

	@ApiOperation("Get a Section")
	@GetMapping(ENDPOINT_ID)
	public ResponseEntity<SectionDto> getSection(@PathVariable(PATH_VARIABLE_ID) final long id) {

		SectionDto result = null;
		try {
			result = sectionService.getSection(id);
			return ResponseEntity.ok().body(result);
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(result);
		}
	}

	@ApiOperation("Get a Section")
	@GetMapping("/by-code")
	public ResponseEntity<List<SectionDto>> getSectionByCode(@RequestParam(name = "code") String code) {

		List<SectionDto> result = null;
		try {
			result = sectionService.getSectionByGeologicalCode(code);
			return ResponseEntity.ok().body(result);
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(result);
		}
	}

	@ApiOperation("Create a Section")
	@PostMapping
	public ResponseEntity<SectionDto> createSection(@Valid @RequestBody SectionSimpleDto sectionSimpleDto) {

		SectionDto result = null;
		try {
			result = sectionService.createSection(sectionSimpleDto);
			return ResponseEntity.ok().body(result);
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (RecordAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(result);
		}
	}

	@ApiOperation("Update a Section")
	@PatchMapping(ENDPOINT_ID)
	public ResponseEntity<SectionDto> updateSection(@PathVariable(PATH_VARIABLE_ID) final long id,
			@Valid @RequestBody SectionSimpleDto sectionSimpleDto) {

		SectionDto result = null;
		try {
			result = sectionService.updateSection(id, sectionSimpleDto);
			return ResponseEntity.ok().body(result);
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(result);
		}
	}

	@ApiOperation("Delete a Section")
	@DeleteMapping(ENDPOINT_ID)
	public ResponseEntity<Void> deleteSection(@PathVariable(PATH_VARIABLE_ID) final long id) {

		try {
			sectionService.deleteSection(id);
			return ResponseEntity.ok().body(null);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}
}
