package com.natlex.natlexgeologicalapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.natlex.natlexgeologicalapi.entities.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

	
	Optional<Section> findOneByName(String name);
	
	@Query("from Section s, Geological g where g.section = s and g.code = :geologicalCode")
    List<Section> findSectionsWithGeologicalCode(@Param("geologicalCode") String geologicalCode);
}
