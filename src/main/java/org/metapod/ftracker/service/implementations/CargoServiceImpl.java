package org.metapod.ftracker.service.implementations;

import lombok.RequiredArgsConstructor;
import org.metapod.ftracker.exception.DataIntegrityException;
import org.metapod.ftracker.model.domain.Cargo;
import org.metapod.ftracker.model.domain.Flight;
import org.metapod.ftracker.model.dto.CargoDto;
import org.metapod.ftracker.model.mapping.Mapper;
import org.metapod.ftracker.repository.CargoRepository;
import org.metapod.ftracker.repository.FlightRepository;
import org.metapod.ftracker.service.CargoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
class CargoServiceImpl implements CargoService {
    private final Mapper mapper;

    private final FlightRepository flightRepository;
    private final CargoRepository cargoRepository;

    @Override
    public void save(List<CargoDto> cargosDtos) throws DataIntegrityException {
        List<Cargo> cargos = new ArrayList<>();

        for(CargoDto cargoDto : cargosDtos)
            cargos.add(createCargo(cargoDto));

        cargoRepository.saveAllAndFlush(cargos);
    }

    private Cargo createCargo(CargoDto cargoDto) throws DataIntegrityException {
        Flight targetFlight = flightRepository.findFlightById(cargoDto.getFlightId())
                .orElseThrow(() -> missingFlightException(cargoDto.getFlightId()));

        Cargo cargo = mapper.mapCargoFromDto(cargoDto);
        cargo.setFlight(targetFlight);

        return cargo;
    }

    private static DataIntegrityException missingFlightException(int flightId) {
        return new DataIntegrityException("Flight with id " + flightId + " does not exist");
    }

    @Override
    public List<CargoDto> getAll() {
        return cargoRepository.findAll().stream()
                .map(mapper::mapDtoFromCargo)
                .toList();
    }
}
