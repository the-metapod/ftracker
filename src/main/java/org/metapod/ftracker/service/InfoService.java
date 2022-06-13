package org.metapod.ftracker.service;

import org.metapod.ftracker.exception.ResourceMissingException;
import org.metapod.ftracker.model.request.AirportTrafficSummaryRequest;
import org.metapod.ftracker.model.request.FlightWeightRequest;
import org.metapod.ftracker.model.response.AirportTrafficSummaryResponse;
import org.metapod.ftracker.model.response.FlightWeightResponse;

public interface InfoService {
    FlightWeightResponse getFlightWeight(FlightWeightRequest request) throws ResourceMissingException;
    AirportTrafficSummaryResponse getAirportTrafficSummary(AirportTrafficSummaryRequest request);
}
