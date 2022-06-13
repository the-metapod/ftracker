package org.metapod.ftracker.service.implementations;

import lombok.RequiredArgsConstructor;
import org.metapod.ftracker.exception.ResourceMissingException;
import org.metapod.ftracker.model.domain.Cargo;
import org.metapod.ftracker.model.domain.Flight;
import org.metapod.ftracker.model.request.AirportTrafficSummaryRequest;
import org.metapod.ftracker.model.request.FlightWeightRequest;
import org.metapod.ftracker.model.response.AirportTrafficSummaryResponse;
import org.metapod.ftracker.model.response.FlightWeightResponse;
import org.metapod.ftracker.model.response.Weight;
import org.metapod.ftracker.repository.FlightRepository;
import org.metapod.ftracker.service.InfoService;
import org.metapod.ftracker.utils.Parcels;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
class InfoServiceImpl implements InfoService {
    private final FlightRepository flightRepository;

    @Override
    public FlightWeightResponse getFlightWeight(FlightWeightRequest request) throws ResourceMissingException {
        Flight flight = flightRepository.findFirstFlightByFlightNumberAndDepartureDate(
                        request.getFlightNumber(),
                        request.getDate())
                .orElseThrow(InfoServiceImpl::newFlightNotFoundException);

        return calculateWeight(flight);
    }

    private FlightWeightResponse calculateWeight(Flight flight) {
        List<Cargo> flightCargos = flight.getCargos();

        long baggageMassKg = Parcels.totalWeightInKilograms(
                flightCargos.stream().flatMap(c -> c.getBaggage().stream()));

        long cargoUnitsMassKg = Parcels.totalWeightInKilograms(
                flightCargos.stream().flatMap(c -> c.getCargo().stream()));

        long totalMassKg = baggageMassKg + cargoUnitsMassKg;

        return new FlightWeightResponse(flight.getId(),
                Weight.fromKilograms(cargoUnitsMassKg),
                Weight.fromKilograms(baggageMassKg),
                Weight.fromKilograms(totalMassKg));
    }

    private static ResourceMissingException newFlightNotFoundException() {
        return new ResourceMissingException("Flight with given number and departure date does not exist");
    }

    @Override
    public AirportTrafficSummaryResponse getAirportTrafficSummary(AirportTrafficSummaryRequest request) {
        String iataCode = request.getIataAirportCode();
        OffsetDateTime dateTime = request.getDate();

        List<Flight> departingFlights = flightRepository.getDepartingFlights(iataCode, dateTime);
        List<Flight> arrivingFlights = flightRepository.getArrivingFlights(iataCode, dateTime);

        int departingBaggagePieces = totalBaggagePieces(departingFlights);
        int arrivingBaggagePieces = totalBaggagePieces(arrivingFlights);

        return new AirportTrafficSummaryResponse(
                departingFlights.size(),
                arrivingFlights.size(),
                departingBaggagePieces,
                arrivingBaggagePieces);
    }

    private static int totalBaggagePieces(List<Flight> flights) {
        return Parcels.totalPieces(
                flights.stream()
                        .flatMap(f -> f.getCargos().stream())
                        .flatMap(c -> c.getBaggage().stream()));
    }
}
