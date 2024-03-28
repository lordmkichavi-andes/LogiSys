package com.logisys.backend.repository;

import com.logisys.backend.model.Puerto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuertoRepository extends JpaRepository<Puerto, Long> {

}
