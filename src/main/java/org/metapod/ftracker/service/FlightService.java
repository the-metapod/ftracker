package org.metapod.ftracker.service;

import org.metapod.ftracker.exception.DataIntegrityException;
import org.metapod.ftracker.model.domain.Flight;
import org.metapod.ftracker.model.dto.FlightDto;

import java.util.List;

public interface FlightService {
    void save(List<FlightDto> flightDtos);
    List<FlightDto> getAll();
}
