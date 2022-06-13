package org.metapod.ftracker.repository;

import org.metapod.ftracker.model.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
    Optional<Flight> findFlightById(int id);

    Optional<Flight> findFirstFlightByFlightNumberAndDepartureDate(int flightNumber, OffsetDateTime departureDate);

    @Query("SELECT f FROM Flight f WHERE f.departureAirportIATACode = ?1 AND f.departureDate < ?2")
    List<Flight> getArrivingFlights(String airportIATACode, OffsetDateTime dateTime);

    @Query("SELECT f FROM Flight f WHERE f.arrivalAirportIATACode = ?1 AND f.departureDate < ?2")
    List<Flight> getDepartingFlights(String airportIATACode, OffsetDateTime dateTime);
}
