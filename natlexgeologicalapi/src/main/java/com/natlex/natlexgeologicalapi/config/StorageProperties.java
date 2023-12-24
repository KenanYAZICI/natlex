package com.natlex.natlexgeologicalapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;


@ConfigurationProperties("storage")
@Data
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String importLocation = "/natleximport";
	
	private String exportLocation = "/natlexexport";


}
