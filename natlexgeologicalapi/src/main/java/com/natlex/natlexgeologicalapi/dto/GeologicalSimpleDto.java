package com.natlex.natlexgeologicalapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class GeologicalSimpleDto {
	
	private String name;
	
	@JsonIgnore
	@Setter(AccessLevel.NONE)
	private boolean nameSet;
	
	private String code;
	
	@JsonIgnore
	@Setter(AccessLevel.NONE)
	private boolean codeSet;
	
	private SectionSimpleDto section;
	
	@JsonIgnore
	@Setter(AccessLevel.NONE)
	private boolean sectionSet;
	
	
	public void setName(String name) {
		this.name = name;
		this.codeSet = true;
	}

	public void setCode(String code) {
		this.code = code;
		this.codeSet = true;
	}

	public void setSection(SectionSimpleDto section) {
		this.section = section;
		this.sectionSet = true;
	}
	

}
