package org.metapod.ftracker.model.mapping;

import org.metapod.ftracker.model.domain.Cargo;
import org.metapod.ftracker.model.domain.Flight;
import org.metapod.ftracker.model.dto.CargoDto;
import org.metapod.ftracker.model.dto.FlightDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    private final ModelMapper mapper = new ModelMapper();

    public Mapper() {
        TypeMap<CargoDto, Cargo> cargoTypeMap = mapper.createTypeMap(CargoDto.class, Cargo.class);
        cargoTypeMap.addMappings(mapper -> mapper.skip(Cargo::setId));
    }

    public Flight mapFlightFromDto(FlightDto flightDto) {
        return mapper.map(flightDto, Flight.class);
    }

    public FlightDto mapDtoFromFlight(Flight flight) {
        return mapper.map(flight, FlightDto.class);
    }

    public Cargo mapCargoFromDto(CargoDto cargoDto) {
        Cargo cargo = mapper.map(cargoDto, Cargo.class);
        attachCargoElements(cargo);
        return cargo;
    }

    private static void attachCargoElements(Cargo cargo) {
        cargo.getBaggage().forEach(b -> b.setCargo(cargo));
        cargo.getCargo().forEach(c -> c.setCargo(cargo));
    }

    public CargoDto mapDtoFromCargo(Cargo cargo) {
        return mapper.map(cargo, CargoDto.class);
    }
}
