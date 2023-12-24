package com.natlex.natlexgeologicalapi.services.impl;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.natlex.natlexgeologicalapi.dto.GeologicalDto;
import com.natlex.natlexgeologicalapi.dto.GeologicalSimpleDto;
import com.natlex.natlexgeologicalapi.entities.Geological;
import com.natlex.natlexgeologicalapi.entities.Section;
import com.natlex.natlexgeologicalapi.exceptions.NotFoundException;
import com.natlex.natlexgeologicalapi.exceptions.RecordAlreadyExistsException;
import com.natlex.natlexgeologicalapi.mapper.SelmaMapper;
import com.natlex.natlexgeologicalapi.repositories.GeologicalRepository;
import com.natlex.natlexgeologicalapi.services.GeologicalService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class GeologicalServiceImpl implements GeologicalService {

	private final GeologicalRepository geologicalRepository;

	@Override
	public GeologicalDto getGeologicalByName(String name) throws NotFoundException {
		return SelmaMapper.INSTANCE.asGeologicalDto(getSectionByNameInner(name));
	}

	private Geological getSectionByNameInner(String name) throws NotFoundException {

		return geologicalRepository.findOneByName(name)
				.orElseThrow(() -> new NotFoundException(Geological.class, name));
	}

	@Override
	public GeologicalDto getGeological(long id) throws NotFoundException {
		return SelmaMapper.INSTANCE.asGeologicalDto(getGeologicalInner(id));
	}

	private Geological getGeologicalInner(long id) throws NotFoundException {
		return geologicalRepository.findById(id).orElseThrow(() -> new NotFoundException(Geological.class, id));
	}

	@Override
	public GeologicalDto createGeological(GeologicalSimpleDto geologicalSimpleDto)
			throws NotFoundException, RecordAlreadyExistsException {
		Geological geological = SelmaMapper.INSTANCE.asGeological(geologicalSimpleDto);
		try {
			return SelmaMapper.INSTANCE.asGeologicalDto(geologicalRepository.save(geological));
		} catch (DataIntegrityViolationException e) {
			throw new RecordAlreadyExistsException(Section.class, geological.getName());
		}
	}

	@Override
	public GeologicalDto updateGeological(long id, GeologicalSimpleDto geologicalSimpleDto) throws NotFoundException {
		geologicalRepository.findById(id).orElseThrow(() -> new NotFoundException(Geological.class, id));
		Geological geological = SelmaMapper.INSTANCE.asGeological(geologicalSimpleDto);
		geological.setId(id);
		return SelmaMapper.INSTANCE.asGeologicalDto(geologicalRepository.save(geological));
	}

	@Override
	public void deleteGeological(long id) {
		geologicalRepository.deleteById(id);
	}

}
