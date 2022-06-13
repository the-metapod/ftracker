package org.metapod.ftracker.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FlightWeightResponse {
    private int flightId;

    private Weight cargoWeight;

    private Weight baggageWeight;

    private Weight totalWeight;
}
