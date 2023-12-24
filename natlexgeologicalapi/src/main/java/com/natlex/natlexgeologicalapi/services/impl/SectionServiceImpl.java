package com.natlex.natlexgeologicalapi.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.natlex.natlexgeologicalapi.dto.SectionDto;
import com.natlex.natlexgeologicalapi.dto.SectionExportDto;
import com.natlex.natlexgeologicalapi.dto.SectionSimpleDto;
import com.natlex.natlexgeologicalapi.entities.Section;
import com.natlex.natlexgeologicalapi.exceptions.NotFoundException;
import com.natlex.natlexgeologicalapi.exceptions.RecordAlreadyExistsException;
import com.natlex.natlexgeologicalapi.mapper.SelmaMapper;
import com.natlex.natlexgeologicalapi.repositories.SectionRepository;
import com.natlex.natlexgeologicalapi.services.SectionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class SectionServiceImpl implements SectionService {

	private final SectionRepository sectionRepository;
//	
//	 @PersistenceContext
//	 private EntityManager entityManager;

	@Override
	public SectionDto getSectionByName(String name) throws NotFoundException {
		return SelmaMapper.INSTANCE.asSectionDto(getSectionByNameInner(name));
	}

	private Section getSectionByNameInner(String name) throws NotFoundException {

		return sectionRepository.findOneByName(name).orElseThrow(() -> new NotFoundException(Section.class, name));
	}

	@Override
	public SectionDto createSection(SectionSimpleDto sectionSimpleDto)
			throws NotFoundException, RecordAlreadyExistsException {
		Section section = SelmaMapper.INSTANCE.asSection(sectionSimpleDto);
		try {
			return SelmaMapper.INSTANCE.asSectionDto(sectionRepository.save(section));
		} catch (DataIntegrityViolationException e) {
			throw new RecordAlreadyExistsException(Section.class, section.getName());
		}
	}

	@Override
	public SectionDto getSection(long id) throws NotFoundException {
		return SelmaMapper.INSTANCE.asSectionDto(getSectionInner(id));
	}
	
	@Override
	@Transactional
	public List<SectionExportDto> getSectionForExport() throws NotFoundException {
		return sectionRepository.findAll().stream().map(item -> {
			return SelmaMapper.INSTANCE.asSectionExportDto(item);
		}).collect(Collectors.toList());
	}

	private Section getSectionInner(long id) throws NotFoundException {
		return sectionRepository.findById(id).orElseThrow(() -> new NotFoundException(Section.class, id));
	}

	@Override
	public SectionDto updateSection(long id, SectionSimpleDto sectionSimpleDto) throws NotFoundException {
		sectionRepository.findById(id).orElseThrow(() -> new NotFoundException(Section.class, id));
		Section section = SelmaMapper.INSTANCE.asSection(sectionSimpleDto);
		section.setId(id);
		return SelmaMapper.INSTANCE.asSectionDto(sectionRepository.save(section));
	}

	@Override
	public void deleteSection(long id) {
		sectionRepository.deleteById(id);
	}

	@Override
	public List<SectionDto> getSectionByGeologicalCode(String geologicalCode) throws NotFoundException {
		return sectionRepository.findSectionsWithGeologicalCode(geologicalCode).stream().map(item -> {
			return SelmaMapper.INSTANCE.asSectionDto(item);
		}).collect(Collectors.toList());
	}

}
