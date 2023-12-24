package com.natlex.natlexgeologicalapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.natlex.natlexgeologicalapi.entities.ImportFile;

@Repository
public interface ImportFileRepository extends JpaRepository<ImportFile, Long>  {

}
