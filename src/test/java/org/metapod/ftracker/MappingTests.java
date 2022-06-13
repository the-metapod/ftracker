package org.metapod.ftracker;

import org.junit.jupiter.api.Test;
import org.metapod.ftracker.model.domain.*;
import org.metapod.ftracker.model.dto.BaggageDto;
import org.metapod.ftracker.model.dto.CargoDto;
import org.metapod.ftracker.model.dto.CargoUnitDto;
import org.metapod.ftracker.model.dto.FlightDto;
import org.metapod.ftracker.model.mapping.Mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

public class MappingTests {
    Mapper mapper = new Mapper();

    @Test
    public void shouldConvertFlightDtoToFlight() {
        OffsetDateTime date = OffsetDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault());
        FlightDto flightDto = new FlightDto(1, 1, "ABC", "XYZ", date);

        Flight flight = mapper.mapFlightFromDto(flightDto);

        assertFieldsEqualIgnoring(flight, flightDto, "id", "cargos");
        assertThat(flight.getId()).isEqualTo(flightDto.getFlightId());
    }

    @Test
    public void shouldConvertFlightToFlightDto() {
        OffsetDateTime date = OffsetDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault());
        Flight flight = new Flight(1, 1234, "ABC", "DEF", date, new ArrayList<>());

        FlightDto flightDto = mapper.mapDtoFromFlight(flight);

        assertFieldsEqualIgnoring(flightDto, flight, "cargos", "flightId");
        assertThat(flightDto.getFlightId()).isEqualTo(flight.getId());
    }

    @Test
    public void shouldConvertCargoDtoToCargo() {
        List<BaggageDto> baggages = Arrays.asList(
                new BaggageDto(1, 1, WeightUnit.kg, 1),
                new BaggageDto(2, 2, WeightUnit.lb, 2));

        List<CargoUnitDto> cargoUnits = Arrays.asList(
                new CargoUnitDto(1, 1, WeightUnit.kg, 1),
                new CargoUnitDto(2, 2, WeightUnit.lb, 2));

        CargoDto cargoDto = new CargoDto(1, baggages, cargoUnits);

        Cargo cargo = mapper.mapCargoFromDto(cargoDto);

        assertThat(cargo.getId()).isEqualTo(0);
        assertThat(cargo.getFlight().getId()).isEqualTo(1);

        forAllPairsOfEqualLists(cargo.getBaggage(), cargoDto.getBaggage(),
                (b, bDto) -> assertFieldsEqualIgnoring(b, bDto, "cargo"));

        assertThat(cargo.getBaggage())
                .extracting(Baggage::getCargo)
                .allMatch(c -> c == cargo);

        forAllPairsOfEqualLists(cargo.getCargo(), cargoDto.getCargo(),
                (c, cDto) -> assertFieldsEqualIgnoring(c, cDto, "cargo"));

        assertThat(cargo.getCargo())
                .extracting(CargoUnit::getCargo)
                .allMatch(c -> c == cargo);
    }

    @Test
    public void shouldConvertCargoToCargoDto() {
        OffsetDateTime date = OffsetDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault());
        Flight flight = new Flight(1, 1234, "ABC", "DEF", date, new ArrayList<>());

        Cargo cargo = new Cargo();

        List<Baggage> baggages = Arrays.asList(
                new Baggage(cargo, 1, 1, WeightUnit.kg, 1),
                new Baggage(cargo, 2, 2, WeightUnit.lb, 2));

        List<CargoUnit> cargoUnits = Arrays.asList(
                new CargoUnit(cargo, 1, 1, WeightUnit.kg, 1),
                new CargoUnit(cargo, 2, 2, WeightUnit.lb, 2));

        cargo.setId(2);
        cargo.setFlight(flight);
        cargo.setBaggage(baggages);
        cargo.setCargo(cargoUnits);

        CargoDto cargoDto = mapper.mapDtoFromCargo(cargo);

        assertThat(cargoDto.getFlightId()).isEqualTo(flight.getId());

        forAllPairsOfEqualLists(cargoDto.getBaggage(), cargo.getBaggage(),
                (bDto, b) -> assertFieldsEqualIgnoring(bDto, b, "cargo"));

        forAllPairsOfEqualLists(cargoDto.getCargo(), cargo.getCargo(),
                (bDto, b) -> assertFieldsEqualIgnoring(bDto, b, "cargo"));
    }

    public void assertFieldsEqualIgnoring(Object actual, Object expected, String... ignoredFields) {
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields(ignoredFields)
                .isEqualTo(expected);
    }

    private static <T, V> void forAllPairsOfEqualLists(List<T> ts, List<V> vs, BiConsumer<T, V> consumer) {
        assertThat(ts.size()).isEqualTo(vs.size())
                .withFailMessage("Lists do not have the same length");

        for(int i = 0; i < ts.size(); i++)
            consumer.accept(ts.get(i), vs.get(i));
    }
}
