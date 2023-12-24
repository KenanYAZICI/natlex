package com.natlex.natlexgeologicalapi.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.natlex.natlexgeologicalapi.dto.GeologicalDto;
import com.natlex.natlexgeologicalapi.dto.GeologicalSimpleDto;
import com.natlex.natlexgeologicalapi.exceptions.NotFoundException;
import com.natlex.natlexgeologicalapi.exceptions.RecordAlreadyExistsException;
import com.natlex.natlexgeologicalapi.services.GeologicalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Geologicals")
@RequestMapping(GeologicalRestController.ENDPOINT)
@AllArgsConstructor
public class GeologicalRestController {
	
	public static final String ENDPOINT = "/api/v1/geologicals";
	public static final String PATH_VARIABLE_ID = "id";
	public static final String ENDPOINT_ID = "/{id}";
	
	@Autowired
	private GeologicalService geologicalService;
	
	@ApiOperation("Get a Geological")
	@GetMapping(ENDPOINT_ID)
	public ResponseEntity<GeologicalDto> getGeological( @PathVariable(PATH_VARIABLE_ID) final long id) {

		GeologicalDto result = null;
		try {
			result = geologicalService.getGeological(id);
			return ResponseEntity.ok().body(result);
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(result);
		}
	}

	@ApiOperation("Create a Geological")
	@PostMapping
	public ResponseEntity<GeologicalDto> createGeological(@Valid @RequestBody GeologicalSimpleDto geologicalSimpleDto) {

		GeologicalDto result = null;
		try {
			result = geologicalService.createGeological(geologicalSimpleDto);
			return ResponseEntity.ok().body(result);
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (RecordAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(result);
		}
	}

	@ApiOperation("Update a Geological")
	@PatchMapping(ENDPOINT_ID)
	public ResponseEntity<GeologicalDto> updateGeological( @PathVariable(PATH_VARIABLE_ID) final long id,
			@Valid @RequestBody GeologicalSimpleDto geologicalSimpleDto) {

		GeologicalDto result = null;
		try {
			result = geologicalService.updateGeological(id, geologicalSimpleDto);
			return ResponseEntity.ok().body(result);
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(result);
		}
	}
	
	@ApiOperation("Delete a Geological")
	@DeleteMapping(ENDPOINT_ID)
	public ResponseEntity<Void> deleteGeological(@PathVariable(PATH_VARIABLE_ID) final long id) {

		try {
			geologicalService.deleteGeological(id);
			return ResponseEntity.ok().body(null);
		}  catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

}
