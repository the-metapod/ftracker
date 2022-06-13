package org.metapod.ftracker.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AirportTrafficSummaryResponse {
    @JsonProperty
    private int departingFlights;

    @JsonProperty
    private int arrivingFlights;

    @JsonProperty
    private int departingBaggagePieces;

    @JsonProperty
    private int arrivingBaggagePieces;
}
