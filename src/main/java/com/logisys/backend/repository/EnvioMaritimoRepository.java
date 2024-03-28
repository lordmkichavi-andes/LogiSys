package com.logisys.backend.repository;

import com.logisys.backend.model.EnvioMaritimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvioMaritimoRepository extends JpaRepository<EnvioMaritimo, Long> {

}