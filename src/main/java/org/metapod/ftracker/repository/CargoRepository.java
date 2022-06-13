package org.metapod.ftracker.repository;

import org.metapod.ftracker.model.domain.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<Cargo, Integer> {

}
