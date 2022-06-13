package org.metapod.ftracker.api;

import lombok.RequiredArgsConstructor;
import org.metapod.ftracker.model.dto.FlightDto;
import org.metapod.ftracker.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightsController {
    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<Void> saveFlights(@RequestBody @Valid List<FlightDto> flights) {
        flightService.save(flights);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<FlightDto>> getFlights() {
        return ResponseEntity.ok(flightService.getAll());
    }
}
