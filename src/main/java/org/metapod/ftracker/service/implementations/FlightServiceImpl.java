package org.metapod.ftracker.service.implementations;

import lombok.RequiredArgsConstructor;
import org.metapod.ftracker.model.domain.Flight;
import org.metapod.ftracker.model.dto.FlightDto;
import org.metapod.ftracker.model.mapping.Mapper;
import org.metapod.ftracker.repository.FlightRepository;
import org.metapod.ftracker.service.FlightService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class FlightServiceImpl implements FlightService {
    private final Mapper mapper;
    private final FlightRepository flightRepository;

    @Override
    public void save(List<FlightDto> flightDtos) {
        List<Flight> flights = flightDtos.stream()
                .map(mapper::mapFlightFromDto)
                .toList();

        flightRepository.saveAllAndFlush(flights);
    }

    @Override
    public List<FlightDto> getAll() {
        return flightRepository.findAll().stream()
                .map(mapper::mapDtoFromFlight)
                .toList();
    }
}
