package com.natlex.natlexgeologicalapi.services.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.natlex.natlexgeologicalapi.config.StorageProperties;
import com.natlex.natlexgeologicalapi.exceptions.StorageException;
import com.natlex.natlexgeologicalapi.services.StorageService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
class StorageServiceImpl implements StorageService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StorageServiceImpl.class);
	
	
	private final StorageProperties properties;
	

	@Override
	public Path store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}
			LocalDateTime localDateTimeNow = LocalDateTime.now();
			//A random guid can be added to file name, to prevent same folder/file name generation if another export starts at the same second
			String folderName =  localDateTimeNow.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
					
			Path destinationFile = Paths.get(properties.getImportLocation()).resolve(
					Paths.get(folderName+File.separator+file.getOriginalFilename()))
					.normalize().toAbsolutePath();
			Files.createDirectories(destinationFile);

			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
			}
			
			return destinationFile;
		}
		catch (IOException e) {
			LOGGER.error("file store error:",e);
			throw new StorageException("Failed to store file.", e);
		}
	}

}
