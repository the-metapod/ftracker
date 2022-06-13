package org.metapod.ftracker.service;

import org.metapod.ftracker.exception.DataIntegrityException;
import org.metapod.ftracker.model.dto.CargoDto;

import java.util.List;

public interface CargoService {
    void save(List<CargoDto> cargoDtos) throws DataIntegrityException;
    List<CargoDto> getAll();
}
