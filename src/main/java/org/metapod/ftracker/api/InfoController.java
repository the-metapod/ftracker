package org.metapod.ftracker.api;

import lombok.RequiredArgsConstructor;
import org.metapod.ftracker.exception.ResourceMissingException;
import org.metapod.ftracker.model.request.AirportTrafficSummaryRequest;
import org.metapod.ftracker.model.request.FlightWeightRequest;
import org.metapod.ftracker.model.response.AirportTrafficSummaryResponse;
import org.metapod.ftracker.model.response.FlightWeightResponse;
import org.metapod.ftracker.service.InfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/info")
@RequiredArgsConstructor
public class InfoController {
    private final InfoService infoService;

    @GetMapping("/weight")
    public ResponseEntity<FlightWeightResponse>
    getFlightWeight(@RequestBody @Valid FlightWeightRequest request) throws ResourceMissingException {
        return ResponseEntity.ok(infoService.getFlightWeight(request));
    }

    @GetMapping("/traffic")
    public ResponseEntity<AirportTrafficSummaryResponse>
    getAirportTrafficSummary(@RequestBody @Valid AirportTrafficSummaryRequest request) {
        return ResponseEntity.ok(infoService.getAirportTrafficSummary(request));
    }
}
