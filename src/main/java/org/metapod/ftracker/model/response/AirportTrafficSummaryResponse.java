package org.metapod.ftracker.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AirportTrafficSummaryResponse {
    private int departingFlights;

    private int arrivingFlights;

    private int departingBaggagePieces;

    private int arrivingBaggagePieces;
}
