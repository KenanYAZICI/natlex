package com.natlex.natlexgeologicalapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.natlex.natlexgeologicalapi.entities.ExportFile;

@Repository
public interface ExportFileRepository extends JpaRepository<ExportFile, Long> {

}
