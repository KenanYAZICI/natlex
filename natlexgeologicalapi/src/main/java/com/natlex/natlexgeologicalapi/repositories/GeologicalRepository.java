package com.natlex.natlexgeologicalapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.natlex.natlexgeologicalapi.entities.Geological;

@Repository
public interface GeologicalRepository extends JpaRepository<Geological, Long> {

	Optional<Geological> findOneByName(String name);
	
	Optional<Geological> findOneByCode(String code);
}
