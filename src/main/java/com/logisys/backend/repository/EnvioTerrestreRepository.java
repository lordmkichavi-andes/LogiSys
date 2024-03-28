package com.logisys.backend.repository;

import com.logisys.backend.model.EnvioTerrestre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvioTerrestreRepository extends JpaRepository<EnvioTerrestre, Long> {

}