package com.natlex.natlexgeologicalapi.services;

import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	public Path store(MultipartFile file);

}
