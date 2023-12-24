package com.natlex.natlexgeologicalapi.services;

import com.natlex.natlexgeologicalapi.dto.GeologicalDto;
import com.natlex.natlexgeologicalapi.dto.GeologicalSimpleDto;
import com.natlex.natlexgeologicalapi.exceptions.NotFoundException;
import com.natlex.natlexgeologicalapi.exceptions.RecordAlreadyExistsException;

public interface GeologicalService {

	public GeologicalDto getGeologicalByName(String name) throws NotFoundException;
	
	public GeologicalDto getGeological(long id) throws NotFoundException;
	
	public GeologicalDto createGeological(GeologicalSimpleDto geologicalSimpleDto) throws NotFoundException, RecordAlreadyExistsException;
	
	public GeologicalDto updateGeological(long id, GeologicalSimpleDto geologicalSimpleDto) throws NotFoundException;

	public void deleteGeological(long id);
}
