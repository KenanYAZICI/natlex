package com.natlex.natlexgeologicalapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.natlex.natlexgeologicalapi.config.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class NatlexGeologicalApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NatlexGeologicalApiApplication.class, args);
	}

}
